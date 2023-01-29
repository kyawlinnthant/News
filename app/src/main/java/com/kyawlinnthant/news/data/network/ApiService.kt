package com.kyawlinnthant.news.data.network

import com.kyawlinnthant.news.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.random.Random

interface ApiService {

    companion object {
        private const val POPULAR = "3/movie/popular"
    }

    @GET(POPULAR)
    suspend fun fetchNews(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,//v3 only support query [ this must be in header shit!! ]
        @Query("page") page: Int = Random.nextInt(from = 1, until = 5)
    ): Response<NewsDto>
}