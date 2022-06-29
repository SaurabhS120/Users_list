package com.example.users.users_data.repoImpl

import android.util.Log
import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.repos.UsersMediatorRepo
import com.example.users.users_data.db_entities.UserDbEntity
import com.example.users.users_data.mappers.UsersMapper
import com.example.users.users_data.repos.UsersLocalRepo
import com.example.users.users_data.repos.UsersRemoteRepo
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UsersRepoImpl @Inject constructor(
    val usersLocalRepo: UsersLocalRepo,
    val usersRemoteRepo: UsersRemoteRepo
) : UsersMediatorRepo {
    override suspend fun getUsers(coroutineContext: CoroutineContext): Observable<List<UsersEntity>> {

        return Observable.create { emitter ->
            fun getRemoteObserver(coroutineContext: CoroutineContext): SingleObserver<List<UsersEntity>> {
                return object : SingleObserver<List<UsersEntity>> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d("state", "remote observer : onSubscribe")
                    }

                    override fun onSuccess(t: List<UsersEntity>) {
                        Log.d("state", "remote observer : onSuccess")
                        emitter.onNext(t)
                        emitter.onComplete()
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
                    }

                    override fun onError(e: Throwable) {
                        Log.d("state", "local observer onError")
                        emitter.onError(e)
                    }

                    override fun onComplete() {
                        Log.d("state", "local observer onComplete")

                    }

                    override fun onSuccess(t: List<UserDbEntity>) {
                        Log.d("state", "local observer onSuccess")
                        if (t.isNotEmpty()) {
                            val entities = UsersMapper.toUsersEntities(t)
                            emitter.onNext(entities)
                            emitter.onComplete()
                        } else {
                            CoroutineScope(coroutineContext).launch {
                                Log.d("state", "local observer fetch remote")
                                usersRemoteRepo.getUsers(coroutineContext)
                                    .subscribe(getRemoteObserver(coroutineContext))
                            }
                        }


                    }

                }
            }
            CoroutineScope(coroutineContext).launch {
                Log.d("state", "local observer Starting")
                usersLocalRepo.getUsers(coroutineContext)
                    .subscribe(getLocalObserver(coroutineContext))
            }
        }
    }


}