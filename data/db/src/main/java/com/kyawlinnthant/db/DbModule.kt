package com.kyawlinnthant.db

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
object DbModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ): NewsDatabase = Room.databaseBuilder(
        context,
        NewsDatabase::class.java,
        News.NEWS_TABLE
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesNewsDao(
        db: NewsDatabase
    ): NewsDao = db.getNewsDao()

}