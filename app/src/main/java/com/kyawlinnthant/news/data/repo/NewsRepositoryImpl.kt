package com.kyawlinnthant.news.data.repo

import com.kyawlinnthant.news.core.Result
import com.kyawlinnthant.news.core.safeApiCall
import com.kyawlinnthant.news.data.db.NewsDao
import com.kyawlinnthant.news.data.ds.PrefDataStore
import com.kyawlinnthant.news.data.ds.ThemeType
import com.kyawlinnthant.news.data.ds.asThemeType
import com.kyawlinnthant.news.data.ds.toInt
import com.kyawlinnthant.news.data.network.ApiService
import com.kyawlinnthant.news.di.DispatcherModule
import com.kyawlinnthant.news.domain.NewsRepository
import com.kyawlinnthant.news.domain.NewsVo
import com.kyawlinnthant.news.domain.toEntity
import com.kyawlinnthant.news.domain.toVo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val apiService: ApiService,
    private val pref: PrefDataStore,
    @DispatcherModule.Io private val io: CoroutineDispatcher
) : NewsRepository {
    override suspend fun fetchNews(): Result<List<NewsVo>> {
        return when (val response = safeApiCall { apiService.fetchNews() }) {
            is Result.Error -> Result.Error(message = response.message)
            is Result.Success -> {
                response.data?.let { dto ->
                    val news = dto.results.map { it.toEntity() }
                    newsDao.insertNews(news)
                }
                Result.Success(data = response.data?.results?.map { it.toVo() })
            }
        }
    }


    override suspend fun readNews(): Flow<List<NewsVo>> {
        return newsDao.readNews().map { db ->
            db.map { it.toVo() }
        }.flowOn(io)
    }

    override suspend fun readSpecificNews(id: Long): Flow<NewsVo> {
        return newsDao.readSpecificNews(id = id).map { it.toVo() }.flowOn(io)
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
            pref.putAppTheme(value = theme.toInt())
        }
    }

    override suspend fun pullTheme(): Flow<ThemeType> {
        return pref.pullAppTheme().map {
            it?.asThemeType() ?: ThemeType.DEFAULT
        }.flowOn(io)
    }
}