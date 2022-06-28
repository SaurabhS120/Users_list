package com.example.users.users_data.repos

import androidx.lifecycle.LiveData
import com.example.user.users_domain.repos.UsersRepo
import com.example.users.users_data.db_entities.UserDbEntity

interface UsersLocalRepo : UsersRepo {
    override suspend fun getUsers(): LiveData<List<UserDbEntity>>
    suspend fun insertAll(users: List<UserDbEntity>)
}