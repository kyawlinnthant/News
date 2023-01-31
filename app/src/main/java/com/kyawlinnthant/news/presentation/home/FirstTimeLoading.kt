package com.kyawlinnthant.news.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun FirstTimeLoadingView() {
    Box(
        modifier = Modifier.fillMaxSize().testTag("first_time_loading"),
        contentAlignment = Alignment.Center,

    ) {
        Text(text = "First time loading")
    }
}