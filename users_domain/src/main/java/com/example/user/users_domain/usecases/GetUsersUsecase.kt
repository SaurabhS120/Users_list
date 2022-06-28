package com.example.user.users_domain.usecases

import androidx.lifecycle.LiveData
import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.repos.UsersMediatorRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUsersUsecase @Inject constructor(val repo: UsersMediatorRepo) {
    suspend fun invoke(): LiveData<List<UsersEntity>> {
        return withContext(Dispatchers.IO) {
            return@withContext repo.getUsers()
        }
    }
}