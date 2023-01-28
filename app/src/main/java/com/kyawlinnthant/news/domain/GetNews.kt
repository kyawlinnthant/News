package com.kyawlinnthant.news.domain

import com.kyawlinnthant.news.core.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNews @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): Flow<Result<List<NewsVo>>> {
        val dbData = repository.readNews()
        val networkData = repository.fetchNews().map {
            when (it) {
                is Result.Error -> TODO()
                is Result.Success -> TODO()
            }
        }

        return flow {

        }

    }
}