package com.kyawlinnthant.news.vm

import com.google.common.truth.Truth.assertThat
import com.kyawlinnthant.news.core.Result
import com.kyawlinnthant.news.data.ds.ThemeType
import com.kyawlinnthant.news.domain.NewsRepository
import com.kyawlinnthant.news.domain.NewsVo
import com.kyawlinnthant.news.presentation.home.HomeUiState
import com.kyawlinnthant.news.presentation.home.HomeViewModel
import com.kyawlinnthant.test_rule.TestCoroutinesRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @get:Rule
    val testRule = TestCoroutinesRule()

    @Test
    fun `read News from db for first time return empty`() = runTest {
        val vm = HomeViewModel(repository = FakeRepository())
        Dispatchers.setMain(StandardTestDispatcher())
        val vmState = vm.uiState.value
        advanceUntilIdle()
        assertThat(vmState).isEqualTo(HomeUiState.HasNews(emptyList()))
    }

    @Test
    fun `read News from network for first time error`() = runTest {
        val repo = FakeRepository()
        val vm = HomeViewModel(repository = repo)
        Dispatchers.setMain(StandardTestDispatcher())
        repo.setNetworkResponse(error = "Something")
        advanceUntilIdle()
        val vmState = vm.uiState.value
        assertThat(vmState).isEqualTo(HomeUiState.FirstTimeError(message = "Something"))
    }

    @Test
    fun `db has data and show data`() = runTest {
        val repo = FakeRepository()
        val vm = HomeViewModel(repository = repo)
        Dispatchers.setMain(StandardTestDispatcher())
        val data = listOf(
            NewsVo(),
            NewsVo()
        )
        repo.setDbData(data = data)
        advanceUntilIdle()
        val vmState = vm.uiState.value
        assertThat(vmState).isEqualTo(HomeUiState.HasNews(data))
    }

    @Test
    fun `theme state`() = runTest {
        val repo = FakeRepository()
        val vm = HomeViewModel(repository = repo)
        Dispatchers.setMain(StandardTestDispatcher())
        repo.putTheme(theme = ThemeType.LIGHT)
        advanceUntilIdle()
        val theme = vm.uiTheme.value
        assertThat(theme).isEqualTo(ThemeType.LIGHT)
    }

    @Test
    fun `dynamic state`() = runTest {
        val repo = FakeRepository()
        val vm = HomeViewModel(repository = repo)
        Dispatchers.setMain(StandardTestDispatcher())
        repo.putDynamic(true)
        advanceUntilIdle()
        val dynamic = vm.uiDynamic.value
        assertThat(dynamic).isEqualTo(true)
    }

    internal class FakeRepository : NewsRepository {
        private var news = mutableListOf<NewsVo>()
        private var specificNews = NewsVo()
        private var isEnabledDynamic = false
        private var themeStatus: ThemeType = ThemeType.DEFAULT
        private var error = ""

        override suspend fun fetchNews(): Result<List<NewsVo>> {
            return if (error.isEmpty()) Result.Success(data = news) else Result.Error(error)
        }

        override suspend fun readNews(): Flow<List<NewsVo>> {
            return flow { emit(news) }
        }

        override suspend fun readSpecificNews(id: Long): Flow<NewsVo> {
            return flow { emit(specificNews) }
        }

        override suspend fun putDynamic(isEnabled: Boolean) {
            isEnabledDynamic = isEnabled
        }

        override suspend fun pullDynamic(): Flow<Boolean> {
            return flow { emit(isEnabledDynamic) }
        }

        override suspend fun putTheme(theme: ThemeType) {
            themeStatus = theme
        }

        override suspend fun pullTheme(): Flow<ThemeType> {
            return flow { emit(themeStatus) }
        }

        fun setNetworkResponse(error: String) {
            this.error = error
        }

        fun setDbData(data: List<NewsVo>) {
            news.addAll(data)
        }

    }

}