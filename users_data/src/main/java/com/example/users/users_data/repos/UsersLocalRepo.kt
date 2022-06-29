package com.example.users.users_data.repos

import com.example.user.users_domain.repos.UsersRepo
import com.example.users.users_data.db_entities.UserDbEntity
import io.reactivex.rxjava3.core.Maybe
import kotlin.coroutines.CoroutineContext

interface UsersLocalRepo : UsersRepo {
    override suspend fun getUsers(coroutineContext: CoroutineContext): Maybe<List<UserDbEntity>>
    suspend fun insertAll(users: List<UserDbEntity>)
}