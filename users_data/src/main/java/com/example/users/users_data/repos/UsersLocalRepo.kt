package com.example.users.users_data.repos

import com.example.user.users_domain.entities.UsersEntity
import com.example.user.users_domain.entities.UsersEntityPage
import com.example.user.users_domain.repos.UsersRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

interface UsersLocalRepo : UsersRepo<Maybe<UsersEntityPage>> {
    fun insertAll(users: List<UsersEntity>): Completable
}