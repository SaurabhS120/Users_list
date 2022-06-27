package com.example.users.users_data.mappers

import com.example.user.users_domain.entities.UsersEntity
import com.example.users.users_data.model.UsersModel

object UsersMapper {
    fun toUsersEntities(model: UsersModel): List<UsersEntity> {
        return model.users?.map { userItem->
            UsersEntity(
                userItem?.id?:0,
                userItem?.firstName?:"",
                userItem?.maidenName?:"",
                userItem?.lastName?:""
            )
        }?: listOf()
    }
}