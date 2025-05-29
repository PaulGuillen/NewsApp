package com.devpaul.shared.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.shared.ui.extension.CenteredSnackBarHost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun <UiState, UiIntent, UiEvent> BaseScreenWithState(
    viewModel: StatefulViewModel<UiState, UiIntent, UiEvent>,
    onInit: ((uiState: UiState, onIntent: (UiIntent) -> Unit) -> Unit)? = null,
    onUiEvent: (UiEvent, (String) -> Unit) -> Unit = { _, _ -> },
    onDefaultError: (Defaults<Nothing>, (String) -> Unit) -> Unit = { _, _ -> },
    content: @Composable (
        padding: PaddingValues,
        uiState: UiState,
        onIntent: (UiIntent) -> Unit,
        showSnackBar: (String) -> Unit
    ) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiStateFlow.collectAsState()

    val showSnackBar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }

    val executeIntent: (UiIntent) -> Unit = { viewModel.executeUiIntent(it) }

    LaunchedEffect(Unit) {
        onInit?.let {
            withContext(Dispatchers.IO) {
                it(uiState, executeIntent)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest {
            onUiEvent(it, showSnackBar)
        }
    }

    Scaffold(
        snackbarHost = { CenteredSnackBarHost(hostState = snackBarHostState) },
        containerColor = Color.Transparent
    ) { padding ->
        content(padding, uiState, executeIntent, showSnackBar)
    }
}