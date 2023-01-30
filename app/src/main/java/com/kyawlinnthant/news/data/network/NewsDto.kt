package com.kyawlinnthant.news.data.network

import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("page") val pageNumber: Int,
    val results: List<NewsData>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
