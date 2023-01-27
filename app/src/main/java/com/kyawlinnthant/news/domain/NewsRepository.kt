package com.kyawlinnthant.news.domain

import com.kyawlinnthant.news.data.db.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNews(): Flow<List<News>>
}