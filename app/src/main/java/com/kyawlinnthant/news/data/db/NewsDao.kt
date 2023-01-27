package com.kyawlinnthant.news.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecificNews(news: News)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<News>)

    @Delete(entity = News::class)
    suspend fun deleteNews(news: News)

    @Query("DELETE FROM ${News.NEWS_TABLE} WHERE id = :id")
    suspend fun deleteSpecificNews(id: String)

    @Query("DELETE FROM ${News.NEWS_TABLE}")
    suspend fun deleteAllNews()

    @Query("SELECT * FROM ${News.NEWS_TABLE}")
    fun readNews(): Flow<List<News>>

    @Query("SELECT * FROM ${News.NEWS_TABLE} WHERE id = :id")
    fun readSpecificNews(id: String): News?
}