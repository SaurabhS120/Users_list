package com.example.users.users_data.repoImpl.local

import androidx.lifecycle.LiveData
import com.example.users.users_data.api.local.UsersRoomDatabase
import com.example.users.users_data.db_entities.UserDbEntity
import com.example.users.users_data.repos.UsersLocalRepo
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class UsersRoomRepoImpl @Inject constructor(val roomDatabase: UsersRoomDatabase) : UsersLocalRepo {
    override suspend fun getUsers(): LiveData<List<UserDbEntity>> {
        return roomDatabase.userDao().getUsers()
    }

    override suspend fun insertAll(users: List<UserDbEntity>) {
        roomDatabase.userDao().insertAll(users)
    }

}