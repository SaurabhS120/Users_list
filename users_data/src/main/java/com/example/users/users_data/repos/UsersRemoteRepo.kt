package com.example.users.users_data.repos

import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.repos.UsersRepo
import io.reactivex.rxjava3.core.Single
import kotlin.coroutines.CoroutineContext

interface UsersRemoteRepo : UsersRepo<Single<List<UsersEntity>>> {

    suspend fun getPageCount(coroutineContext: CoroutineContext): Int

}