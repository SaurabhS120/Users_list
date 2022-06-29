package com.example.user.users_domain.repos

import com.example.user.users_domain.entities.UsersEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlin.coroutines.CoroutineContext

interface UsersMediatorRepo : UsersRepo {
    override suspend fun getUsers(
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Observable<List<UsersEntity>>
}