package com.example.user.users_domain.usecases

import com.example.user.users_domain.repos.UsersRepo
import com.example.user.users_domain.responses.UsersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUsersUsecase @Inject constructor(val repo: UsersRepo) {
    suspend fun invoke() : UsersResponse{
        return withContext(Dispatchers.IO){
            return@withContext repo.getUsers()
        }
    }
}