package com.kyawlinnthant.news.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.news.domain.NewsRepository
import com.kyawlinnthant.news.domain.NewsVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: NewsRepository,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    private val vmState = MutableStateFlow(NewsVo())
    val uiState get() = vmState.asStateFlow()

    init {
        savedStateHandle.get<Long>(key = "id")?.let {
            readNews(id = it)
        }
    }

    private fun readNews(id: Long) {
        viewModelScope.launch {
            repository.readSpecificNews(id).collect {
                vmState.emit(it)
            }
        }
    }
}