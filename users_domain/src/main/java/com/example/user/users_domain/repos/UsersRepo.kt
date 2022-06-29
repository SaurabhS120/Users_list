package com.example.user.users_domain.repos

import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlin.coroutines.CoroutineContext

interface UsersRepo {
    suspend fun getUsers(
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Any
}