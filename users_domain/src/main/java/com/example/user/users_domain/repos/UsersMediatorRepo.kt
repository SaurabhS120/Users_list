package com.example.user.users_domain.repos

import com.example.user.users_domain.entities.UsersEntityPage
import io.reactivex.rxjava3.core.Observable

interface UsersMediatorRepo : UsersRepo<Observable<UsersEntityPage>>