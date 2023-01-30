package com.kyawlinnthant.news.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.news.data.ds.ThemeType
import com.kyawlinnthant.news.domain.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val vmTheme = MutableStateFlow(ThemeType.DEFAULT)
    val uiTheme get() = vmTheme.asStateFlow()
    private val vmDynamic = MutableStateFlow(true)
    val uiDynamic get() = vmDynamic.asStateFlow()

    init {
        getThemePreference()
        getDynamicPreference()
    }

    private fun getThemePreference() {
        viewModelScope.launch {
            repository.pullTheme().collect {
                vmTheme.emit(it)
            }
        }
    }

    private fun getDynamicPreference() {
        viewModelScope.launch {
            repository.pullDynamic().collect {
                vmDynamic.emit(it)
            }
        }
    }
}