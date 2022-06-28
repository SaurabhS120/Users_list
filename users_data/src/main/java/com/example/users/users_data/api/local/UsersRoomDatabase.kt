package com.example.users.users_data.api.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.users.users_data.dao.UsersDao
import com.example.users.users_data.db_entities.UserDbEntity

@Database(entities = [UserDbEntity::class], version = 1)
abstract class UsersRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UsersDao
}