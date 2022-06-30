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
import kotlin.coroutines.coroutineContext

class UsersRepoImpl @Inject constructor(
    val usersLocalRepo: UsersLocalRepo,
    val usersRemoteRepo: UsersRemoteRepo,
    val preferences: PageDetailsPreferences
) : UsersMediatorRepo {
    override suspend fun getPageCount(): Int {
        var pageCount = preferences.getPageCount()
        if (pageCount < 0) {
            pageCount = usersRemoteRepo.getPageCount(coroutineContext)
            preferences.setPageCount(pageCount)
        }
        return pageCount
    }

    override suspend fun getUsers(
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Observable<UsersEntityPage> {
        val pageCount = getPageCount()
        return Observable.create { emitter ->
            Observable.range(1, pageCount)
                .map {
                    Log.d("state", "local getUsersPage $it")
                    usersLocalRepo.getUsersPage(it, coroutineContext, compositeDisposable)
                }
                .map { prev ->
                    Single.create<UsersEntityPage> { emitter ->
                        prev.subscribe {
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