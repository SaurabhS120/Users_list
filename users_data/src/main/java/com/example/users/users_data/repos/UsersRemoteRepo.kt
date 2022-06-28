package com.example.users.users_data.repos

import com.example.user.users_domain.repos.UsersRepo
import com.example.user.users_domain.responses.UsersResponse

interface UsersRemoteRepo : UsersRepo {
    override suspend fun getUsers(): UsersResponse
}