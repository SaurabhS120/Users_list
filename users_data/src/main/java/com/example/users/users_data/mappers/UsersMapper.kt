package com.example.users.users_data.mappers

import com.example.user.users_domain.entities.UsersEntity
import com.example.users.users_data.db_entities.UserDbEntity
import com.example.users.users_data.model.UsersModel

object UsersMapper {
    fun toUsersEntities(model: UsersModel): List<UsersEntity> {
        return model.users?.map { userItem ->
            UsersEntity(
                userItem?.id ?: 0,
                userItem?.firstName ?: "",
                userItem?.maidenName ?: "",
                userItem?.lastName ?: "",
                userItem?.phone ?: "",
                userItem?.birthDate ?: "",
                userItem?.age ?: 0,
                userItem?.bloodGroup ?: ""
            )
        } ?: listOf()
    }

    fun toUsersEntities(daos: List<UserDbEntity>): List<UsersEntity> {
        return daos.map { dao ->
            UsersEntity(
                dao.id,
                dao.first_name,
                dao.maiden_name,
                dao.last_name,
                dao.phone,
                dao.birthDate,
                dao.age,
                dao.bloodGroup
            )
        }
    }

    fun toUsersDbEntities(entities: List<UsersEntity>?): List<UserDbEntity> {
        return entities?.map { entity ->
            UserDbEntity(
                entity.id,
                entity.firstName,
                entity.maidenName,
                entity.lastName,
                entity.phone,
                entity.birthDate,
                entity.age,
                entity.bloodGroup
            )
        } ?: listOf()
    }

    fun dbEntityToUserEntity(entity: UserDbEntity): UsersEntity {
        return UsersEntity(
            entity.id,
            entity.first_name,
            entity.maiden_name,
            entity.last_name,
            entity.phone,
            entity.birthDate,
            entity.age,
            entity.bloodGroup
        )
    }
}