package com.kyawlinnthant.news.data.network

import com.kyawlinnthant.news.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val MOVIES_POPULAR = "3/movie/popular"
    }

    @GET(MOVIES_POPULAR)
    suspend fun fetchNews(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,//v3 only support query [ this must be in header shit!! ]
        @Query("page") page: Int = 1
    ): Response<NewsDto>
}