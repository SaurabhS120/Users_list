package com.example.users.users_data.repoImpl

import android.util.Log
import com.example.user.users_domain.entities.UsersEntityPage
import com.example.user.users_domain.repos.UsersMediatorRepo
import com.example.users.users_data.api.local.PageDetailsPreferences
import com.example.users.users_data.repos.UsersLocalRepo
import com.example.users.users_data.repos.UsersRemoteRepo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UsersRepoImpl @Inject constructor(
    val usersLocalRepo: UsersLocalRepo,
    val usersRemoteRepo: UsersRemoteRepo,
    val preferences: PageDetailsPreferences
) : UsersMediatorRepo {
    override fun getPageCount(): Single<Int> {
        return Single.just(preferences.getPageCount())
            .flatMap {
                Single.create<Int> { emitter ->
                    if (it >= 0) emitter.onSuccess(it)
                    else usersRemoteRepo.getPageCount()
                        .onErrorComplete {
                            if (emitter.isDisposed.not()) {
                                emitter.onError(it)
                            }
                            true
                        }
                        .doOnSuccess {
                            emitter.onSuccess(it)
                        }
                        .blockingSubscribe()
                }
                    .map {
                        preferences.setPageCount(it)
                        it
                    }
            }
//            .compose {
//                val data = it.blockingGet()
//                if (data < 0) {
//                    val count = usersRemoteRepo.getPageCount()
//                        .onErrorReturn {
//                            -1
//                        }
//                        .blockingGet()
//                    if (count < 0) {
//                        Single.error(Throwable("Network error"))
//                    } else {
//                        preferences.setPageCount(count)
//                        Single.just(count)
//                    }
//                } else {
//                    Single.just(data)
//                }
//            }.map {
//                Log.d("state", "get page observer count pref : $it")
//                it
//            }
//        var pageCount = preferences.getPageCount()
//        if (pageCount < 0) {
//            pageCount = usersRemoteRepo.getPageCount(coroutineContext)
//            preferences.setPageCount(pageCount)
//        }
    }

    override suspend fun getUsers(
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Observable<UsersEntityPage> {
        val pageCount = getPageCount().blockingGet()
        return Observable.create { emitter ->
            Observable.range(1, pageCount)
                .map {
                    Log.d("state", "local getUsersPage $it")
                    usersLocalRepo.getUsersPage(it, coroutineContext, compositeDisposable)
                }
                .map { prev ->
                    Single.create<UsersEntityPage> { emitter ->
                        prev.subscribe {

                            Log.d("state", "local response page:${it.pageNo} empty:${it.isEmpty()}")
                            if (it.isEmpty().not()) {
                                emitter.onSuccess(it)
                            } else {
                                usersRemoteRepo.getUsersPage(
                                    it.pageNo,
                                    coroutineContext,
                                    compositeDisposable
                                )
                                    .doOnSuccess {
                                        Log.d("state", "usersRemoteRepo doOnSuccess")
                                        emitter.onSuccess(it)
                                    }
                                    .subscribe()
                            }
                        }
                    }
                }
                .map {
                    it.doOnSuccess {
                        usersLocalRepo.insertAll(it.entities)
                        emitter.onNext(it)
                    }
                        .blockingSubscribe()
                }

                .doFinally {
                    emitter.onComplete()
                }
                .subscribe()
        }
    }

    override fun getUsersPage(
        pageNo: Int,
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Observable<UsersEntityPage> {
        TODO("Not yet implemented")
    }


}