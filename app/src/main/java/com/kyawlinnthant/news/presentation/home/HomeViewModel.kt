package com.kyawlinnthant.news.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.news.core.Result
import com.kyawlinnthant.news.domain.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: NewsRepository
) : ViewModel() {
    private val vmState = MutableStateFlow(HomeViewModelState())
    val uiState = vmState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.toUiState()
        )

    init {
        fetchNews()
    }

    fun fetchNews() {
        vmState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repo.fetchNews().collectLatest { result ->
                when (result) {
                    is Result.Error -> vmState.update {
                        it.copy(
                            error = result.message,
                            isLoading = false,
                        )
                    }
                    is Result.Success -> vmState.update {
                        it.copy(
                            news = result.data!!,
                            isLoading = false,
                            error = ""
                        )
                    }
                }
            }
        }
    }

}