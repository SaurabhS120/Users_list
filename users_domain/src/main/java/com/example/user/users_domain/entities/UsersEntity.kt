package com.example.user.users_domain.entities

data class UsersEntity(val id:Int, val firstName:String, val maidenName:String, val lastName:String)
{
    fun idString() = id.toString()
}