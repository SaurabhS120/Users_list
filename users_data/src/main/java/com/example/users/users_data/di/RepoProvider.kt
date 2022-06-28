package com.example.users.users_data.di

import com.example.user.users_domain.repos.UsersMediatorRepo
import com.example.users.users_data.repoImpl.UsersRepoImpl
import com.example.users.users_data.repoImpl.local.UsersRoomRepoImpl
import com.example.users.users_data.repoImpl.remote.UsersRetrofitRepoImpl
import com.example.users.users_data.repos.UsersLocalRepo
import com.example.users.users_data.repos.UsersRemoteRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepoProvider {
    @Binds
    fun bindUsersMediatorRepo(repo: UsersRepoImpl): UsersMediatorRepo

    @Binds
    fun bindRemoteRepo(repo: UsersRetrofitRepoImpl): UsersRemoteRepo

    @Binds
    fun bindLocalRepo(repo: UsersRoomRepoImpl): UsersLocalRepo

}