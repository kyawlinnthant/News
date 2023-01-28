package com.kyawlinnthant.news.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val topBarScrollState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topBarScrollState)
    val listState = rememberLazyListState()
    val isScrolled = remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    val snackState: SnackbarHostState = remember { SnackbarHostState() }

    val vm: HomeViewModel = hiltViewModel()
    val state = vm.uiState.collectAsState()
    Scaffold(
        snackbarHost = { NewsSnackHost(state = snackState) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "News")
                },
                actions = {

                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )

            )
        },
    ) { contentPadding ->
        HomeContent(
            uiState = state.value,
            modifier = Modifier.padding(contentPadding),
            listState = listState,
            onRetry = vm::fetchNews
        )
    }
}

@Composable
fun NewsSnackHost(
    state: SnackbarHostState,
    snackBar: @Composable (SnackbarData) -> Unit = { Snackbar(snackbarData = it) }
) {
    SnackbarHost(
        hostState = state,
        modifier = Modifier
            .systemBarsPadding()
            // Limit the Snack bar width for large screens
            .wrapContentWidth(align = Alignment.Start)
            .widthIn(max = 550.dp),
        snackbar = snackBar
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    listState: LazyListState,
    onRetry: () -> Unit,
) {
    when (uiState) {
        is HomeUiState.HasNews -> {
            HasNewsView(
                modifier = modifier,
                data = uiState.news,
                listState = listState
            )
        }
        HomeUiState.FirstTimeLoading -> {
            FirstTimeLoadingView()
        }
        is HomeUiState.NetworkError -> {

        }
        is HomeUiState.FirstTimeError -> {
            FirstTimeErrorView(
                modifier = modifier,
                message = uiState.message,
                onRetry = onRetry
            )
        }
    }
}