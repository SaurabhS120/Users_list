package com.example.user.users_domain.usecases

import com.example.user.users_domain.repos.UsersMediatorRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPageCountUsecase @Inject constructor(val repo: UsersMediatorRepo) {
    suspend fun invoke(): Int {
        return repo.getPageCount()
    }
}