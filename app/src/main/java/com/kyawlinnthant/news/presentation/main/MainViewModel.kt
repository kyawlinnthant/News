package com.kyawlinnthant.news.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    var isDarkTheme: Flow<Boolean?> = emptyFlow()

    init {
        viewModelScope.launch {
            isDarkTheme = flow { emit(true) }
        }
    }
}