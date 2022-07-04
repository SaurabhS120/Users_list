package com.example.users.users_data.api.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomApi {
    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext applicationContext: Context): UsersRoomDatabase {
        return Room.databaseBuilder(
            applicationContext,
            UsersRoomDatabase::class.java, "users_db"
        )
            .addMigrations(UserDatabaseMigrations.MIGRATION_1_TO_2)
            .addMigrations(UserDatabaseMigrations.MIGRATION_2_TO_3)
            .addMigrations(UserDatabaseMigrations.MIGRATION_3_TO_4)
            .addMigrations(UserDatabaseMigrations.MIGRATION_4_TO_5)
            .build()
    }
}