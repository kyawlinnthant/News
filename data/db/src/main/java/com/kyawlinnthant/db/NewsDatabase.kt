package com.kyawlinnthant.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [News::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao

    companion object {
        const val NEWS_DB = "news.db"
    }
}