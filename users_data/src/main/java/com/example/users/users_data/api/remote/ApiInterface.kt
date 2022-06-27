package com.example.users.users_data.api.remote

import com.example.users.users_data.model.UsersModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/users")
    suspend fun getUsers():Response<UsersModel>
}