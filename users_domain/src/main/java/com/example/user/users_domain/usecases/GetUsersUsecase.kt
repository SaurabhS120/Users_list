package com.example.user.users_domain.usecases

import com.example.user.users_domain.repos.UsersRepo

class GetUsersUsecase(val repo: UsersRepo) {
    suspend fun invoke() = repo.getUsers()
}