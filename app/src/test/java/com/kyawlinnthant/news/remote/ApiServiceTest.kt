package com.kyawlinnthant.news.remote

import com.google.common.truth.Truth.assertThat
import com.google.gson.stream.MalformedJsonException
import com.kyawlinnthant.news.BuildConfig
import com.kyawlinnthant.news.data.network.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection


@OptIn(ExperimentalCoroutinesApi::class)
class ApiServiceTest {
    private lateinit var service: ApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    private fun enqueueResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val mockResponse = MockResponse()
        val source = inputStream.source().buffer()
        mockWebServer.enqueue(
            mockResponse.setBody(source.readString(Charsets.UTF_8))
        )
    }

    @Test
    fun `fetch news return success with 200`() = runTest {
        enqueueResponse(fileName = "fetch-news-200.json")
        val response = service.fetchNews()
        val request = mockWebServer.takeRequest()
        //test request
        assertThat(request.method).isEqualTo("GET")
        val expectedUrl = arrayListOf<String>().apply {
            add("/" + ApiService.POPULAR + "?api_key=${BuildConfig.API_KEY}?page=1")
            add("/" + ApiService.POPULAR + "?api_key=${BuildConfig.API_KEY}?page=2")
            add("/" + ApiService.POPULAR + "?api_key=${BuildConfig.API_KEY}?page=3")
            add("/" + ApiService.POPULAR + "?api_key=${BuildConfig.API_KEY}?page=4")
            add("/" + ApiService.POPULAR + "?api_key=${BuildConfig.API_KEY}?page=5")
        }
//        assertThat(request.path).isEqualTo("/" + ApiService.POPULAR + "?api_key=${BuildConfig.API_KEY}&page=1")
        assertThat(expectedUrl).contains(request.path)
        //test response
        val code = response.code()
        val body = response.body()
        assertThat(code).isEqualTo(HttpURLConnection.HTTP_OK)
        val dto = body!!
        assertThat(dto.results.size).isEqualTo(20)
        assertThat(dto.pageNumber).isEqualTo(1)
        assertThat(dto.totalPages).isEqualTo(36867)
        assertThat(dto.totalResults).isEqualTo(737324)

    }

    @Test(expected = MalformedJsonException::class)
    fun `malformed json throws exception`() = runTest {
        enqueueResponse("malformed.json")
        service.fetchNews()
    }

    @Test
    fun `fetch news - client error 4xx`() = runTest {
        val expectedResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(expectedResponse)
        val actual = service.fetchNews()
        val code = actual.code()
        assertThat(code).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND)
    }
    @Test
    fun `fetch news - server error 5xx`() = runTest {
        val expectedResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(expectedResponse)
        val actual = service.fetchNews()
        val code = actual.code()
        assertThat(code).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
    }
}