package com.example.users.users_data.repoImpl.remote

import com.example.user.users_domain.responses.UsersResponse
import com.example.users.users_data.api.remote.ApiInterface
import com.example.users.users_data.mappers.UsersMapper
import com.example.users.users_data.model.UsersModel
import com.example.users.users_data.repos.UsersRemoteRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRetrofitRepoImpl @Inject constructor(val api: ApiInterface) : UsersRemoteRepo {
    override suspend fun getUsers(): UsersResponse {
        val response = api.getUsers()
        if (response.isSuccessful) {
            val responseData: UsersModel? = response.body()
            responseData?.let {
                val entities = UsersMapper.toUsersEntities(it)
                return UsersResponse.Success(entities)
            }
        }
        return UsersResponse.Failure("Server error")
    }

}