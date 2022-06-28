package com.example.user.users_domain.repos

interface UsersRepo {
    suspend fun getUsers(): Any
}