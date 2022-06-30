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
        @Query("skip") offset: Int,
        @Query("limit") limit: Int
    ): Response<UsersModel>


    @GET("/users")
    suspend fun getUsersEmpty(
        @Query("skip") offset: Int = 0,
        @Query("limit") limit: Int = 0
    ): Response<UsersModel>

}