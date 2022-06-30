package com.example.users.users_data.api.remote

import com.example.users.users_data.model.UsersModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/users")
    fun getUsers(
        @Query("skip") offset: Int,
        @Query("limit") limit: Int
    ): Call<UsersModel>


    @GET("/users")
    fun getUsersEmpty(
        @Query("skip") offset: Int = 0,
        @Query("limit") limit: Int = 0
    ): Call<UsersModel>

}