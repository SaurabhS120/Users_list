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
import io.reactivex.rxjava3.schedulers.Schedulers
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
    }

    override fun getUsers(
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Observable<UsersEntityPage> {
        val pageCount = getPageCount().blockingGet()
        return Observable.create<UsersEntityPage?> { mainEmitter ->
            Observable.range(1, pageCount)
                .map {
                    Log.d("state", "local getUsersPage $it")
                    usersLocalRepo.getUsersPage(it, coroutineContext, compositeDisposable)
                        .blockingGet()
                        ?: UsersEntityPage(-1, listOf())
                }
                .compose { oldObservable ->
                    Observable.create<UsersEntityPage> { emitter ->
                        oldObservable.onErrorComplete {
                            if (emitter.isDisposed.not()) {
                                emitter.onError(it)
                            }
                            true
                        }
                        oldObservable
                            .onErrorComplete {
                                emitter.onError(it)
                                true
                            }
                            .forEach {
                                if (it.isEmpty().not()) {
                                    emitter.onNext(it)
                                } else {
                                    emitter.onNext(
                                        usersRemoteRepo.getUsersPage(
                                            it.pageNo,
                                            coroutineContext,
                                            compositeDisposable
                                        )
                                            .onErrorReturn {
                                            if (emitter.isDisposed.not()) {
                                                emitter.onError(it)
                                            }
                                            UsersEntityPage(-1, listOf())
                                        }
                                        .doOnSuccess {
                                            usersLocalRepo.insertAll(it.entities)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(Schedulers.io())
                                                .blockingSubscribe()
                                        }
                                        .blockingGet()
                                )

                            }
                        }
                        emitter.onComplete()
                    }
                }

                .onErrorComplete {
                    Log.d("state", "users repo impl fail 2")
                    if (mainEmitter.isDisposed.not()) {
                        mainEmitter.onError(it)
                    }
                    true
                }
                .doOnNext {
                    if (it.isEmpty().not()) {
                        mainEmitter.onNext(it)
                    }

                }
                .onErrorComplete {
                    if (mainEmitter.isDisposed.not()) {
                        mainEmitter.onError(it)
                    }
                    true
                }
                .doOnComplete {
                    mainEmitter.onComplete()
                }
                .blockingSubscribe()
        }
            .map {
                Log.d("state", "returning data from repo : $it")
                it
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