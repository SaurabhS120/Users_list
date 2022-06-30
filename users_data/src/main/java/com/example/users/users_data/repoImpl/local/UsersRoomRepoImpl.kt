package com.example.users.users_data.repoImpl.local

import android.util.Log
import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.entities.UsersEntityPage
import com.example.users.users_data.PageConfig
import com.example.users.users_data.api.local.UsersRoomDatabase
import com.example.users.users_data.mappers.UsersMapper
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
//    override suspend fun getUsers(
//        coroutineContext: CoroutineContext,
//        compositeDisposable: CompositeDisposable
//    ): Maybe<List<UserDbEntity>> {
//        return roomDatabase.userDao().getUsers()
//    }

    override fun getUsersPage(
        pageNo: Int,
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Maybe<UsersEntityPage> {
        val start = ((pageNo - 1) * PageConfig.PAGE_SIZE) + 1
        val end = ((pageNo) * PageConfig.PAGE_SIZE)
        Log.d("state", "getUserRoom start:$start end:$end")
        return roomDatabase.userDao().getUsersBetween(start, end).map { data ->
            UsersEntityPage(pageNo, UsersMapper.toUsersEntities(data))
        }
    }

    override fun insertAll(users: List<UsersEntity>) {
        val converted = UsersMapper.toUsersDbEntities(users)
        roomDatabase.userDao().insertAll(converted)
    }

    override suspend fun getUsers(
        coroutineContext: CoroutineContext,
        compositeDisposable: CompositeDisposable
    ): Maybe<UsersEntityPage> {
        TODO("Not yet implemented")
    }

}