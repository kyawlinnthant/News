package com.kyawlinnthant.news.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.news.core.Result
import com.kyawlinnthant.news.domain.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val vmState = MutableStateFlow(HomeViewModelState())
    val uiState = vmState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = vmState.value.toUiState()
        )

    private val vmEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent get() = vmEvent.asSharedFlow()

    init {
        readNews()
        fetchNews()
    }

    private fun readNews() {
        viewModelScope.launch {
            repository.readNews().collect { list ->
                Timber.tag("hey.db.collect").d("${list.size}")
                vmState.update {
                    it.copy(
                        news = list,
                        isLoading = it.isLoading,
                        error = it.error
                    )
                }
            }
        }

    }

    fun fetchNews() {
        vmState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = repository.test()) {
                is Result.Error -> {
                    Timber.tag("hey.network.error").d(result.message)
                    vmState.update {
                        it.copy(
                            error = result.message,
                            isLoading = false,
                            news = it.news
                        )
                    }
                    vmEvent.emit(HomeUiEvent.NetworkError(message = result.message))
                }
                is Result.Success -> {
                    Timber.tag("hey.network.success").d(result.data!!.size.toString())
                    vmState.update {
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