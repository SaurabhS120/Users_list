package com.example.user.users_domain.repos

import kotlin.coroutines.CoroutineContext

interface UsersRepo {
    suspend fun getUsers(coroutineContext: CoroutineContext): Any
}