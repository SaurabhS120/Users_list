package com.example.user.users_domain.repos

import com.example.user.users_domain.entities.UsersEntityPage
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface UsersMediatorRepo : UsersRepo<Observable<UsersEntityPage>> {
    fun getPageCount(): Single<Int>
}