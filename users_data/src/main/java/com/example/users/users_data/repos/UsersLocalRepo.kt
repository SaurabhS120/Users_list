package com.example.users.users_data.repos

import com.example.user.users_domain.repos.UsersRepo
import com.example.users.users_data.db_entities.UserDbEntity
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlin.coroutines.CoroutineContext

interface UsersLocalRepo : UsersRepo {
    override suspend fun getUsers(
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Maybe<List<UserDbEntity>>

    suspend fun insertAll(users: List<UserDbEntity>)
}