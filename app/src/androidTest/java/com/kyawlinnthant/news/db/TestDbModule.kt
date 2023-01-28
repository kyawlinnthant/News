package com.kyawlinnthant.news.db

import android.content.Context
import androidx.room.Room
import com.kyawlinnthant.news.data.db.DbModule
import com.kyawlinnthant.news.data.db.NewsDao
import com.kyawlinnthant.news.data.db.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DbModule::class]
)
object TestDbModule {

    @Provides
    @Singleton
    fun provideInMemoryDb(
        @ApplicationContext context: Context
    ): NewsDatabase = Room.inMemoryDatabaseBuilder(
        context,
        NewsDatabase::class.java
    ).allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideTestDao(
        db: NewsDatabase
    ): NewsDao = db.getNewsDao()

}