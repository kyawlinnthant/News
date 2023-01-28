package com.kyawlinnthant.news.data.repo

import com.kyawlinnthant.news.core.Result
import com.kyawlinnthant.news.core.safeApiCall
import com.kyawlinnthant.news.data.db.News
import com.kyawlinnthant.news.data.db.NewsDao
import com.kyawlinnthant.news.data.ds.PrefDataStore
import com.kyawlinnthant.news.data.ds.ThemeType
import com.kyawlinnthant.news.data.ds.ThemeType.Light.asThemeType
import com.kyawlinnthant.news.data.network.ApiService
import com.kyawlinnthant.news.di.DispatcherModule
import com.kyawlinnthant.news.domain.NewsRepository
import com.kyawlinnthant.news.domain.NewsVo
import com.kyawlinnthant.news.domain.toEntity
import com.kyawlinnthant.news.domain.toVo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val apiService: ApiService,
    private val pref: PrefDataStore,
    @DispatcherModule.Io private val io: CoroutineDispatcher
) : NewsRepository {
    override suspend fun fetchNews(): Flow<Result<List<NewsVo>>> {
        val response = safeApiCall { apiService.fetchNews() }
        return flow {
            emit(response)
        }.onEach { result ->
            result.data?.let { dto ->
                val news = dto.results.map { data -> data.toEntity() }
                newsDao.insertNews(news = news)
            }
        }.map {
            when (it) {
                is Result.Error -> Result.Error(it.message)
                is Result.Success -> Result.Success(data = it.data?.results?.map { dto -> dto.toVo() })
            }
        }.flowOn(io)
    }


    override suspend fun readNews(): Flow<List<News>> {
        return newsDao.readNews().flowOn(io)
    }

    override suspend fun putDynamic(isEnabled: Boolean) {
        withContext(io) {
            pref.putEnabledDynamicColor(status = isEnabled)
        }
    }

    override suspend fun pullDynamic(): Flow<Boolean> {
        return pref.pullEnabledDynamicColor().map {
            it ?: true //default is TRUE
        }.flowOn(io)
    }

    override suspend fun putTheme(theme: ThemeType) {
        withContext(io) {
            pref.putAppTheme(value = theme.value)
        }
    }

    override suspend fun pullTheme(): Flow<ThemeType> {
        return pref.pullAppTheme().map {
            it?.asThemeType() ?: ThemeType.Default
        }.flowOn(io)
    }
}