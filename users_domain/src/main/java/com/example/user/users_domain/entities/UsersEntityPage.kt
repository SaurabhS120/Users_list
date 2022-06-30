package com.example.user.users_domain.entities

data class UsersEntityPage(val pageNo: Int, val entities: List<UsersEntity>) {
    fun isEmpty() = entities.isEmpty()
}