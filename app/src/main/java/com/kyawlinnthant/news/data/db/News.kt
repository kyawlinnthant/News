package com.kyawlinnthant.news.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kyawlinnthant.news.data.db.News.Companion.NEWS_TABLE

@Entity(
    tableName = NEWS_TABLE,
)
data class News(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val isAdult: Boolean,
    val backdropUrl: String?,
    val posterUrl: String?,
    val language: String,
    val title: String,
    val overview: String,
    val popularity: Double,
    val releasedDate: String,
    val hasVideo: Boolean,
    val averageVote: Double,
    val votedCount: Int
) {
    companion object {
        const val NEWS_TABLE = "news"
    }
}
