package com.kyawlinnthant.news.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val topBarScrollState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topBarScrollState)
    val listState = rememberLazyListState()
    val snackState: SnackbarHostState = remember { SnackbarHostState() }

    val vm: HomeViewModel = hiltViewModel()
    val state = vm.uiState.collectAsState()
    val event = vm.uiEvent

    LaunchedEffect(key1 = event) {
        event.collectLatest {
            when (it) {
                is HomeUiEvent.NetworkError -> {
                   val result =  snackState.showSnackbar(
                        message = it.message,
                        duration = SnackbarDuration.Indefinite,
                        actionLabel = "Retry",
                        withDismissAction = true,
                    )
                    when (result) {
                        SnackbarResult.Dismissed -> Unit
                        SnackbarResult.ActionPerformed -> vm.fetchNews()
                    }
                }


            }
        }
    }
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
            Timber.tag("hey.ui.has").d("here")
            HasNewsView(
                modifier = modifier,
                data = uiState.news,
                listState = listState
            )
        }
        HomeUiState.FirstTimeLoading -> {
            Timber.tag("hey.ui.first.load").d("here")
            FirstTimeLoadingView()
        }
        is HomeUiState.FirstTimeError -> {
            Timber.tag("hey.ui.first.error").d("here")
            FirstTimeErrorView(
                modifier = modifier,
                message = uiState.message,
                onRetry = onRetry
            )
        }
    }
}