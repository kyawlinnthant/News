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

fun NewsData.toVo() = NewsVo(
    id = this.id,
    title = this.title,
    description = this.overview,
    url = this.poster ?: "",
    voteCount = this.voteCount,
    date = this.date
)
