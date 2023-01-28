package com.kyawlinnthant.news.domain

import com.kyawlinnthant.news.data.db.News
import com.kyawlinnthant.news.data.network.NewsData

data class NewsVo(
    val id: Long,
    val title: String,
    val description: String,
    val url: String,
    val voteCount: Int,
    val date: String,
)

fun News.toVo() = NewsVo(
    id = this.id,
    title = this.title,
    description = this.overview,
    url = this.posterUrl ?: "",
    voteCount = this.votedCount,
    date = this.releasedDate
)

fun NewsData.toEntity() = News(
    id = this.id,
    isAdult = this.isAdult,
    backdropUrl = this.backdrop,
    posterUrl = this.poster,
    language = this.language,
    title = this.title,
    overview = this.overview,
    popularity = this.popularity,
    releasedDate = this.date,
    hasVideo = this.video,
    averageVote = this.voteAverage,
    votedCount = this.voteCount
)

fun NewsData.toVo() = NewsVo(
    id = this.id,
    title = this.title,
    description = this.overview,
    url = this.poster ?: "",
    voteCount = this.voteCount,
    date = this.date
)
