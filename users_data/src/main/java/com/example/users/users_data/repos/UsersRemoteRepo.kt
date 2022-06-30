package com.example.users.users_data.repos

import com.example.user.users_domain.entities.UsersEntityPage
import com.example.user.users_domain.repos.UsersRepo
import io.reactivex.rxjava3.core.Single

interface UsersRemoteRepo : UsersRepo<Single<UsersEntityPage>> {

    fun getPageCount(): Single<Int>

}