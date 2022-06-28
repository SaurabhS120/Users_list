package com.example.users.users_data.repoImpl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.repos.UsersMediatorRepo
import com.example.user.users_domain.responses.UsersResponse
import com.example.users.users_data.mappers.UsersMapper
import com.example.users.users_data.repos.UsersLocalRepo
import com.example.users.users_data.repos.UsersRemoteRepo
import javax.inject.Inject

class UsersRepoImpl @Inject constructor(
    val usersLocalRepo: UsersLocalRepo,
    val usersRemoteRepo: UsersRemoteRepo
) : UsersMediatorRepo {
    override suspend fun getUsers(): LiveData<List<UsersEntity>> {
        val db_livedata = usersLocalRepo.getUsers()
        if (db_livedata.value.isNullOrEmpty()) {
            val remoteresponse = usersRemoteRepo.getUsers()
            when (remoteresponse) {
                is UsersResponse.Success -> {
                    Log.d("network", "getting data")
                    val dbEntities = UsersMapper.toUsersDbEntities(remoteresponse.response)
                    usersLocalRepo.insertAll(dbEntities)
                }
            }
        }
        return Transformations.map(db_livedata) {
            UsersMapper.toUsersEntities(it)
        }
    }
}