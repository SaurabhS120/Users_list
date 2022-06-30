package com.example.user.users_domain.usecases

import com.example.user.users_domain.repos.UsersMediatorRepo
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPageCountUsecase @Inject constructor(val repo: UsersMediatorRepo) {
    fun invoke(): Single<Int> {
        return repo.getPageCount()
    }
}