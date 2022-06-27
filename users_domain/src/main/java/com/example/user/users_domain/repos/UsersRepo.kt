package com.example.user.users_domain.repos

import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.responses.UsersResponse

interface UsersRepo {
    suspend fun getUsers():UsersResponse
}