package com.kyawlinnthant.news.domain

import com.kyawlinnthant.news.core.Result
import com.kyawlinnthant.news.data.ds.ThemeType
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun fetchNews(): Result<List<NewsVo>>
    suspend fun readNews(): Flow<List<NewsVo>>
    suspend fun putDynamic(isEnabled: Boolean)
    suspend fun pullDynamic(): Flow<Boolean>
    suspend fun putTheme(theme: ThemeType)
    suspend fun pullTheme(): Flow<ThemeType>
}