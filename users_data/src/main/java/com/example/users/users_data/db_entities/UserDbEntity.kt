package com.example.users.users_data.db_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDbEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "first_name") val first_name: String,
    @ColumnInfo(name = "maiden_name") val maiden_name: String,
    @ColumnInfo(name = "last_name") val last_name: String
)
