package com.example.users.users_data.di

import com.example.user.users_domain.repos.UsersRepo
import com.example.users.users_data.repoImpl.UsersRetrofitRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepoProvider {
    @Binds
    fun bindUsersRepo(repo : UsersRetrofitRepoImpl):UsersRepo

}