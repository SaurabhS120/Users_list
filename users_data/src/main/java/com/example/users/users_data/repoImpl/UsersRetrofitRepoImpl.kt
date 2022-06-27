package com.example.users.users_data.repoImpl

import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.repos.UsersRepo
import com.example.user.users_domain.responses.UsersResponse
import com.example.users.users_data.api.remote.ApiInterface
import com.example.users.users_data.mappers.UsersMapper
import com.example.users.users_data.model.UsersModel

class UsersRetrofitRepoImpl(val api:ApiInterface) :UsersRepo{
    override suspend fun getUsers(): UsersResponse {
        val response = api.getUsers()
        if (response.isSuccessful){
            val responseData: UsersModel? = response.body()
            responseData?.let {
                val entities = UsersMapper.toUsersEntities(it)
                return UsersResponse.Success(entities)
            }
        }
        return UsersResponse.Failure("Server error")
    }

}