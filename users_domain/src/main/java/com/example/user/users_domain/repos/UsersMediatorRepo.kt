package com.example.user.users_domain.repos

import com.example.user.users_domain.entities.UsersEntity
import io.reactivex.rxjava3.core.Observable
import kotlin.coroutines.CoroutineContext

interface UsersMediatorRepo : UsersRepo {
    override suspend fun getUsers(coroutineContext: CoroutineContext): Observable<List<UsersEntity>>
}