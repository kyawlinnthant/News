package com.kyawlinnthant.news.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.news.core.Result
import com.kyawlinnthant.news.data.ds.ThemeType
import com.kyawlinnthant.news.domain.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    private val dynamicSetting = MutableStateFlow(false)
    val uiDynamic get() = dynamicSetting.asStateFlow()

    private val themeSetting = MutableStateFlow(ThemeType.DEFAULT)
    val uiTheme get() = themeSetting.asStateFlow()

    var shouldShowDialog = mutableStateOf(false)
        private set

    init {
        getThemePreference()
        getDynamicPreference()
        readNews()
        fetchNews()
    }

    private fun getThemePreference() {
        viewModelScope.launch {
            repository.pullTheme().collect {
                themeSetting.value = it
            }
        }
    }

    private fun getDynamicPreference() {
        viewModelScope.launch {
            repository.pullDynamic().collect {
                dynamicSetting.value = it
            }
        }
    }

    private fun readNews() {
        viewModelScope.launch {
            repository.readNews().collect { list ->
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
            when (val result = repository.fetchNews()) {
                is Result.Error -> {
                    vmState.update {
                        it.copy(
                            error = result.message,
                            isLoading = false,
                            news = it.news
                        )
                    }
                    delay(500L)
                    if (vmState.value.news.isNotEmpty())
                        vmEvent.emit(HomeUiEvent.NetworkError(message = result.message))
                }
                is Result.Success -> {
                    vmState.update {
                        it.copy(
                            news = it.news,
                            isLoading = false,
                            error = ""
                        )
                    }
                }
            }
        }
    }

    fun setDialogVisibility(shouldShow: Boolean) {
        shouldShowDialog.value = shouldShow
    }

    fun setTheme(status: ThemeType) {
        viewModelScope.launch {
            repository.putTheme(status)
        }
    }

    fun setDynamic(isEnabled: Boolean) {
        viewModelScope.launch {
            repository.putDynamic(isEnabled)
        }
    }
}