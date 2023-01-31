package com.kyawlinnthant.news.repository

import com.google.common.truth.Truth.assertThat
import com.kyawlinnthant.news.core.Result
import com.kyawlinnthant.news.data.db.News
import com.kyawlinnthant.news.data.db.NewsDao
import com.kyawlinnthant.news.data.ds.PrefDataStore
import com.kyawlinnthant.news.data.ds.asThemeType
import com.kyawlinnthant.news.data.network.ApiService
import com.kyawlinnthant.news.data.network.NewsDto
import com.kyawlinnthant.news.data.repo.NewsRepositoryImpl
import com.kyawlinnthant.news.domain.NewsVo
import com.kyawlinnthant.news.domain.toVo
import com.kyawlinnthant.test_rule.TestCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepositoryTest {

    private lateinit var service: ApiService
    private lateinit var newsDao: NewsDao
    private lateinit var prefStore: PrefDataStore
    private var repository: NewsRepositoryImpl? = null

    @get:Rule
    val testRule = TestCoroutinesRule()

    @Before
    fun setup() {
        service = mock(ApiService::class.java)
        newsDao = mock(NewsDao::class.java)
        prefStore = mock(PrefDataStore::class.java)
        repository = NewsRepositoryImpl(
            newsDao = newsDao,
            apiService = service,
            pref = prefStore,
            io = testRule.testDispatcher
        )
    }

    @After
    fun teardown() {
        repository = null
    }

    @Test
    fun `fetchNews successfully transform data`() = runTest {
        val mockResponse = NewsDto(
            pageNumber = 1,
            results = emptyList(),
            totalPages = 1,
            totalResults = 1
        )
        `when`(service.fetchNews()).thenReturn(Response.success(mockResponse))
        val actual = repository?.fetchNews()
        assertThat(actual as Result.Success).isEqualTo(Result.Success<List<NewsVo>>(emptyList()))

    }

    @Test
    fun `fetchNews error`() = runTest {
        `when`(service.fetchNews()).thenReturn(
            Response.error(
                500,
                "error response".toResponseBody("txt".toMediaTypeOrNull())
            )
        )
        val actual = repository?.fetchNews()
        assertThat((actual as Result.Error)).isEqualTo(Result.Error("Something's Wrong!"))
    }

    @Test
    fun `read news from db successfully transform to vo`() = runTest {
        `when`(newsDao.readNews()).thenReturn(flowOf(emptyList()))
        val actual = repository?.readNews()?.first()
        assertThat(actual).isEmpty()
    }

    @Test
    fun `no specific room return null`() = runTest {
        `when`(newsDao.readSpecificNews(1)).thenReturn(emptyFlow())
        val actual = repository?.readSpecificNews(1)?.firstOrNull()
        assertThat(actual).isNull()
    }

    @Test
    fun `correct id for specific room return correct vo`() = runTest {
        val entity = News(
            id = 1L,
            isAdult = false,
            backdropUrl = null,
            language = "es",
            posterUrl = "poster",
            title = "Title",
            overview = "overview",
            popularity = 0.0,
            releasedDate = "123",
            hasVideo = false,
            averageVote = 0.0,
            votedCount = 0
        )
        `when`(newsDao.readSpecificNews(1)).thenReturn(flowOf(entity))
        val actual = repository?.readSpecificNews(1)?.first()
        assertThat(actual).isEqualTo(entity.toVo())
    }

    @Test
    fun `dynamic color correctly return`() = runTest {
        `when`(prefStore.pullEnabledDynamicColor()).thenReturn(flowOf(true))
        val actual = repository?.pullDynamic()?.first()
        assertThat(actual).isTrue()
    }

    @Test
    fun `theme status successfully transform to enum`() = runTest {
        //1 = DEFAULT
        `when`(prefStore.pullAppTheme()).thenReturn(flowOf(1))
        val actual = repository?.pullTheme()?.first()
        assertThat(actual).isEqualTo(1.asThemeType())
    }

}