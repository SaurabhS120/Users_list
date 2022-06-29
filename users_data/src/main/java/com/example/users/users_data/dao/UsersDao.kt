package com.example.users.users_data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.users.users_data.db_entities.UserDbEntity
import io.reactivex.rxjava3.core.Maybe

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserDbEntity>)

    @Query("select * from users where id BETWEEN :start AND :end")
    fun getUsersBetween(start: Int, end: Int): Maybe<List<UserDbEntity>>

    @Query("select * from users")
    fun getUsers(): Maybe<List<UserDbEntity>>
}