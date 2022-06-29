package com.example.users.users_data.repoImpl.local

import com.example.users.users_data.api.local.UsersRoomDatabase
import com.example.users.users_data.db_entities.UserDbEntity
import com.example.users.users_data.repos.UsersLocalRepo
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class UsersRoomRepoImpl @Inject constructor(val roomDatabase: UsersRoomDatabase) : UsersLocalRepo {
    override suspend fun getUsers(
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Maybe<List<UserDbEntity>> {
        return roomDatabase.userDao().getUsers()
    }

    override suspend fun insertAll(users: List<UserDbEntity>) {
        roomDatabase.userDao().insertAll(users)
    }

}