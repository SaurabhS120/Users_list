package com.example.user.users_domain.repos

import androidx.lifecycle.LiveData
import com.example.user.users_domain.entities.UsersEntity

interface UsersMediatorRepo : UsersRepo {
    override suspend fun getUsers(): LiveData<List<UsersEntity>>
}