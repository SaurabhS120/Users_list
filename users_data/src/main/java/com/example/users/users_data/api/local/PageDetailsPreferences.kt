package com.example.users.users_data.api.local

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class PageDetailsPreferences @Inject constructor(@ApplicationContext val applicationContext: Context) {
    val FILE_NAME = "PAGE_DETAILS_SHARED_PREF"
    val PAGE_COUNT_KEY = "PAGE_COUNT_KEY"
    fun getPageCount(): Int {
        val sharedPreferences =
            applicationContext.getSharedPreferences(PAGE_COUNT_KEY, Context.MODE_PRIVATE)
        val count = sharedPreferences.getInt(PAGE_COUNT_KEY, -1)

        Log.d("state", "get page count pref : $count")
        return count
    }

    fun setPageCount(pages: Int) {
        Log.d("state", "set page count pref : $pages")
        val sharedPreferences =
            applicationContext.getSharedPreferences(PAGE_COUNT_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putInt(PAGE_COUNT_KEY, pages)
            apply()
        }
    }

}