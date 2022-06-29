package com.example.user.users_domain.repos

import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlin.coroutines.CoroutineContext

interface UsersRepo<OBSERVER> {
    suspend fun getUsers(
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): OBSERVER

    fun getUsersPage(
        pageNo: Int,
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): OBSERVER
}