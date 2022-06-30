package com.example.users.users_data.repos

import com.example.user.users_domain.entities.UsersEntityPage
import com.example.user.users_domain.repos.UsersRepo
import com.example.users.users_data.db_entities.UserDbEntity
import io.reactivex.rxjava3.core.Maybe

interface UsersLocalRepo : UsersRepo<Maybe<UsersEntityPage>> {
    suspend fun insertAll(users: List<UserDbEntity>)
}