package com.kyawlinnthant.news.data.repo

import com.kyawlinnthant.news.data.db.News
import com.kyawlinnthant.news.data.db.NewsDao
import com.kyawlinnthant.news.data.network.ApiService
import com.kyawlinnthant.news.di.DispatcherModule
import com.kyawlinnthant.news.domain.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val apiService: ApiService,
    @DispatcherModule.Io private val io: CoroutineDispatcher
) : NewsRepository {
    override suspend fun getNews(): Flow<List<News>> {
        return emptyFlow()
    }
}