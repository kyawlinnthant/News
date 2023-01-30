package com.kyawlinnthant.news.presentation.home

import com.kyawlinnthant.news.domain.NewsVo

sealed interface HomeUiState {
    object FirstTimeLoading : HomeUiState
    data class FirstTimeError(val message: String) : HomeUiState
    data class HasNews(val news: List<NewsVo>) : HomeUiState
}

data class HomeViewModelState(
    val news: List<NewsVo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
) {
    fun toUiState(): HomeUiState {
        return when {
            news.isEmpty() && isLoading -> HomeUiState.FirstTimeLoading
            news.isEmpty() && error.isNotEmpty() -> HomeUiState.FirstTimeError(message = error)
            else -> HomeUiState.HasNews(news = news)
        }
    }
}

sealed interface HomeUiEvent {
    data class NetworkError(val message: String) : HomeUiEvent
    data class NavigateToDetail(val id: Long) : HomeUiEvent
}
