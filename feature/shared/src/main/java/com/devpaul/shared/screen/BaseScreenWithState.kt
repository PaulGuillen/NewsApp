package com.devpaul.shared.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.shared.ui.extension.CenteredSnackBarHost
import kotlinx.coroutines.launch

@Composable
fun <UiState, UiIntent, UiEvent> BaseScreenWithState(
    viewModel: StatefulViewModel<UiState, UiIntent, UiEvent>,
    onInit: (() -> UiIntent)? = null,
    onUiEvent: (UiEvent, (String) -> Unit) -> Unit = { _, _ -> },
    content: @Composable (
        padding: PaddingValues,
        uiState: UiState,
        onIntent: (UiIntent) -> Unit,
        showSnackBar: (String) -> Unit
    ) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val uiState = viewModel.uiState.collectAsState().value
    val uiEvent = viewModel.uiEvent.collectAsState(initial = null).value

    val showSnackBar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(Unit) {
        onInit?.let { viewModel.executeUiIntent(it()) }
    }

    LaunchedEffect(uiEvent) {
        uiEvent?.let { onUiEvent(it, showSnackBar) }
    }

    Scaffold(
        snackbarHost = { CenteredSnackBarHost(hostState = snackBarHostState) },
        containerColor = Color.Transparent
    ) { padding ->
        content(padding, uiState, viewModel::executeUiIntent, showSnackBar)
    }
}