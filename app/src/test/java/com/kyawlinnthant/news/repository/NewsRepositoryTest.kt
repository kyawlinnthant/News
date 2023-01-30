package com.kyawlinnthant.news.repository

import com.google.common.truth.Truth.assertThat
import com.kyawlinnthant.news.core.Result
import com.kyawlinnthant.news.data.db.NewsDao
import com.kyawlinnthant.news.data.ds.PrefDataStore
import com.kyawlinnthant.news.data.network.ApiService
import com.kyawlinnthant.news.data.network.NewsDto
import com.kyawlinnthant.news.data.repo.NewsRepositoryImpl
import com.kyawlinnthant.news.domain.NewsVo
import com.kyawlinnthant.test_rule.TestCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
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
        val response = repository?.fetchNews()
        assertThat(response).isEqualTo(Result.Success<List<NewsVo>>(emptyList()))

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
//        assertThat((actual as Result.Error).message).isEqualTo(Response.error(500,"error response"))
    }


}