package com.kyawlinnthant.news.data.network

import com.google.gson.annotations.SerializedName


data class NewsData(
    val id: Long,
    @SerializedName("adult")
    val isAdult: Boolean,
    @SerializedName("backdrop_path")
    val backdrop: String?,
    @SerializedName("poster_path")
    val poster: String?,
    @SerializedName("genre_ids")
    val genres: List<Int>,
    @SerializedName("original_language")
    val language: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val title: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("release_date")
    val date: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)
