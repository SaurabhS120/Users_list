package com.example.users.users_data.api.local

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object UserDatabaseMigrations {

    val MIGRATION_1_TO_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.apply {
                Log.d("state", "clearing table")
                execSQL("DELETE FROM users")
                Log.d("state", "adding column")
                execSQL("ALTER TABLE users ADD COLUMN phone TEXT NOT NULL DEFAULT ''")
                Log.d("state", "migration success commit")
            }

        }

    }

    val MIGRATION_2_TO_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.apply {
                Log.d("state", "clearing table")
                execSQL("DELETE FROM users")
                Log.d("state", "adding column")
                execSQL("ALTER TABLE users ADD COLUMN birth_date TEXT NOT NULL DEFAULT ''")
                Log.d("state", "migration success commit")
            }

        }

    }

    val MIGRATION_3_TO_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.apply {
                Log.d("state", "clearing table")
                execSQL("DELETE FROM users")
                Log.d("state", "adding column")
                execSQL("ALTER TABLE users ADD COLUMN age INTEGER  NOT NULL DEFAULT 0")
                execSQL("ALTER TABLE users ADD COLUMN blood_group TEXT  NOT NULL DEFAULT ''")
                Log.d("state", "migration success commit")
            }

        }

    }

}