package com.example.users.users_data.api.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {
    const val baseUrl = "https://dummyjson.com/"
    private var api : ApiInterface? = null
    fun getRetrofitApi(): ApiInterface {
        return api?:
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}