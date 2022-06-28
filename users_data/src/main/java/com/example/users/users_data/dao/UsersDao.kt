package com.example.users.users_data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.users.users_data.db_entities.UserDbEntity

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserDbEntity>)

//    @Query("select ${PageConfig.PAGE_SIZE} from users where id>:offset ORDER BY id ASC")
//    fun getUsers(offset:Int):LiveData<List<UserDbEntity>?>

    @Query("select * from users")
    fun getUsers(): LiveData<List<UserDbEntity>>
}