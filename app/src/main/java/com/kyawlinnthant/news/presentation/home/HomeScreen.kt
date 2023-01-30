package com.kyawlinnthant.news.presentation.home

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kyawlinnthant.news.data.ds.ThemeType
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen() {
    val topBarScrollState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topBarScrollState)
    val listState = rememberLazyListState()
    val snackState: SnackbarHostState = remember { SnackbarHostState() }

    val vm: HomeViewModel = hiltViewModel()
    val state = vm.uiState.collectAsState()
    val event = vm.uiEvent
    val shouldShowDialog = vm.shouldShowDialog.value
    val themeType = vm.uiTheme.collectAsState()
    val isDynamic = vm.uiDynamic.collectAsState()

    if (shouldShowDialog) {
        SettingsDialog(
            onDismiss = {
                vm.setDialogVisibility(false)
            },
            themeType = themeType.value,
            isEnabledDynamic = isDynamic.value,
            onChangeDarkThemeConfig = vm::setTheme,
            onChangeDynamicEnabled = vm::setDynamic
        )
    }

    LaunchedEffect(key1 = event) {
        event.collectLatest {
            when (it) {
                is HomeUiEvent.NetworkError -> {
                    val result = snackState.showSnackbar(
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
                    IconButton(onClick = {
                        vm.setDialogVisibility(true)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
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
        is HomeUiState.FirstTimeError -> {
            FirstTimeErrorView(
                modifier = modifier,
                message = uiState.message,
                onRetry = onRetry
            )
        }
    }
}

@Composable
fun SettingsDialog(
    themeType: ThemeType,
    isEnabledDynamic: Boolean,
    isAboveS: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
    onDismiss: () -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: ThemeType) -> Unit,
    onChangeDynamicEnabled: (isEnabled: Boolean) -> Unit,
) {

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {

            Column {
                Divider()

                Column(Modifier.selectableGroup()) {
                    Text(
                        text = "Dark mode preference",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    SettingsDialogThemeChooserRadio(
                        text = "System default",
                        selected = themeType == ThemeType.DEFAULT,
                        onClick = { onChangeDarkThemeConfig(ThemeType.DEFAULT) }
                    )
                    SettingsDialogThemeChooserRadio(
                        text = "Light",
                        selected = themeType == ThemeType.LIGHT,
                        onClick = { onChangeDarkThemeConfig(ThemeType.LIGHT) }
                    )
                    SettingsDialogThemeChooserRadio(
                        text = "Dark",
                        selected = themeType == ThemeType.DARK,
                        onClick = { onChangeDarkThemeConfig(ThemeType.DARK) }
                    )
                }
                if (isAboveS) {
                    Divider()
                    Column(Modifier.selectableGroup()) {
                        Text(
                            text = "Dynamic preference",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                        SettingsDialogThemeChooserCheck(
                            text = "Enabled dynamic color",
                            selected = isEnabledDynamic,
                            onChecked = onChangeDynamicEnabled
                        )
                    }
                }
            }

        },
        confirmButton = {

            TextButton(onClick = onDismiss) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 8.dp)

                )
            }

        }
    )
}

@Composable
fun SettingsDialogThemeChooserRadio(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@Composable
fun SettingsDialogThemeChooserCheck(
    text: String,
    selected: Boolean,
    onChecked: (Boolean) -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = selected, onCheckedChange = onChecked)
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}