package com.example.users.users_data.api.remote

import com.example.users.users_data.model.UsersModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/users")
    suspend fun getUsers(): Response<UsersModel>

    @GET("/users")
    suspend fun getUsers(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<UsersModel>
}