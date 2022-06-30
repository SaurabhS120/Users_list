package com.example.user.users_domain.usecases

import com.example.user.users_domain.repos.UsersMediatorRepo
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPageCountUsecase @Inject constructor(val repo: UsersMediatorRepo) {
    suspend fun invoke(): Single<Int> {
        return withContext(Dispatchers.IO) {
            return@withContext repo.getPageCount()
        }
    }
}