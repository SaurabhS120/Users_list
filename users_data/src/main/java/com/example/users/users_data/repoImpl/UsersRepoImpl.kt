package com.example.users.users_data.repoImpl

import android.util.Log
import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.repos.UsersMediatorRepo
import com.example.users.users_data.api.local.PageDetailsPreferences
import com.example.users.users_data.db_entities.UserDbEntity
import com.example.users.users_data.mappers.UsersMapper
import com.example.users.users_data.repos.UsersLocalRepo
import com.example.users.users_data.repos.UsersRemoteRepo
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UsersRepoImpl @Inject constructor(
    val usersLocalRepo: UsersLocalRepo,
    val usersRemoteRepo: UsersRemoteRepo,
    val preferences: PageDetailsPreferences
) : UsersMediatorRepo {
    override suspend fun getUsers(
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Observable<List<UsersEntity>> {

        return Observable.create { emitter ->

            CoroutineScope(coroutineContext).launch {
                fun getRemoteObserver(coroutineContext: CoroutineContext): SingleObserver<List<UsersEntity>> {
                    return object : SingleObserver<List<UsersEntity>> {
                        override fun onSubscribe(d: Disposable) {
                            Log.d("state", "remote observer : onSubscribe")
                            compositeDisposable.add(d)
                        }

                        override fun onSuccess(t: List<UsersEntity>) {
                            val text: Int? = if (t.size > 1) {
                                t[0].id
                            } else null
                            Log.d("state", "remote observer onSuccess $text")
                            emitter.onNext(t)
                            val dbEntities = UsersMapper.toUsersDbEntities(t)
                            CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                                usersLocalRepo.insertAll(dbEntities)
                            }
                        }

                        override fun onError(e: Throwable) {
                            Log.d("state", "remote observer : onError")
                            emitter.onError(e)
                        }

                    }
                }

                fun getLocalObserver(coroutineContext: CoroutineContext): MaybeObserver<List<UserDbEntity>> {
                    return object : MaybeObserver<List<UserDbEntity>> {
                        override fun onSubscribe(d: Disposable) {
                            Log.d("state", "local observer onSubscribe")
                            compositeDisposable.add(d)
                        }

                        override fun onError(e: Throwable) {
                            Log.d("state", "local observer onError")
                            emitter.onError(e)
                        }

                        override fun onComplete() {
                            Log.d("state", "local observer onComplete")

                        }

                        override fun onSuccess(t: List<UserDbEntity>) {
                            val text: Int? = if (t.size > 1) {
                                t[0].id
                            } else null
                            Log.d("state", "local observer onSuccess $text")
                            if (t.isNotEmpty()) {
                                val entities = UsersMapper.toUsersEntities(t)
                                emitter.onNext(entities)
                            } else {
                                CoroutineScope(coroutineContext).launch {
                                    Log.d("state", "local observer fetch remote")
                                    usersRemoteRepo.getUsers(coroutineContext, compositeDisposable)
                                        .subscribe(getRemoteObserver(coroutineContext))
                                }
                            }


                        }

                    }
                }
                Log.d("state", "local observer Starting")
                var page_count = preferences.getPageCount()
                if (page_count == -1) {
                    try {
                        val newCount = usersRemoteRepo.getPageCount(coroutineContext)
                        preferences.setPageCount(newCount)
                        page_count = newCount
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                Observable.range(1, page_count)
                    .map {
                        Log.d("state", "calling observer $it")

                        usersLocalRepo.getUsersPage(it, coroutineContext, compositeDisposable)
                    }
                    .apply {
                        blockingForEach { it.blockingSubscribe(getLocalObserver(coroutineContext)) }
                        doOnError {
                            emitter.onError(it)
                        }
                        doFinally { emitter.onComplete() }
                    }
            }
        }
    }

    override fun getUsersPage(
        pageNo: Int,
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Observable<List<UsersEntity>> {
        TODO("Not yet implemented")
    }


}