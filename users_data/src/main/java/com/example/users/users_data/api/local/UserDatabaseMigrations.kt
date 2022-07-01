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
}