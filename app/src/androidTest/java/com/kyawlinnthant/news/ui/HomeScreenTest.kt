package com.kyawlinnthant.news.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.kyawlinnthant.news.data.ds.ThemeType
import com.kyawlinnthant.news.presentation.home.HomeContent
import com.kyawlinnthant.news.presentation.home.HomeUiState
import com.kyawlinnthant.news.presentation.theme.NewsTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun firstTimeLoadingTest() {
        composeTestRule.setContent {
            NewsTheme(themePreference = ThemeType.DEFAULT, isDynamicEnabled = true) {
                HomeContent(
                    uiState = HomeUiState.FirstTimeLoading,
                    listState = rememberLazyListState(),
                    onRetry = { },
                    onItemClicked = {})
            }
        }
        composeTestRule.onNodeWithText("first_time_loading").assertExists()
    }
    @Test
    fun firstTimeErrorTest() {
        composeTestRule.setContent {
            NewsTheme(themePreference = ThemeType.DEFAULT, isDynamicEnabled = true) {
                HomeContent(
                    uiState = HomeUiState.FirstTimeError(message = "error"),
                    listState = rememberLazyListState(),
                    onRetry = { },
                    onItemClicked = {})
            }
        }
        composeTestRule.onNodeWithText("first_time_error").assertExists()
    }
    @Test
    fun hasNews() {
        composeTestRule.setContent {
            NewsTheme(themePreference = ThemeType.DEFAULT, isDynamicEnabled = true) {
                HomeContent(
                    uiState = HomeUiState.HasNews(news = emptyList()),
                    listState = rememberLazyListState(),
                    onRetry = { },
                    onItemClicked = {})
            }
        }
        composeTestRule.onNodeWithText("has_news").assertExists()
    }
}