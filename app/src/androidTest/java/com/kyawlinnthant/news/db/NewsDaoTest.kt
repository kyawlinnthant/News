package com.kyawlinnthant.news.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.kyawlinnthant.news.data.db.News
import com.kyawlinnthant.news.data.db.NewsDao
import com.kyawlinnthant.news.data.db.NewsDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class NewsDaoTest {
    @get:Rule
    val testRule = HiltAndroidRule(this)

    private lateinit var dao: NewsDao

    @Inject
    lateinit var db: NewsDatabase

    @Before
    fun setup() {
        testRule.inject()
        dao = db.getNewsDao()
    }

    @After
    fun teardown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insert_successfully() = runTest {
        val news = listOf(
            News(
                id = 123,
                isAdult = false,
                backdropUrl = null,
                posterUrl = null,
                language = "us",
                title = "T1",
                overview = "",
                popularity = 0.0,
                releasedDate = "",
                hasVideo = false,
                averageVote = 0.0,
                votedCount = 0
            ),
            News(
                id = 1234,
                isAdult = false,
                backdropUrl = null,
                posterUrl = null,
                language = "us",
                title = "T1",
                overview = "",
                popularity = 0.0,
                releasedDate = "",
                hasVideo = false,
                averageVote = 0.0,
                votedCount = 0
            )
        )
        dao.insertNews(news = news)
        val result = dao.readNews().first()
        assertThat(result.size).isEqualTo(news.size)
    }
}