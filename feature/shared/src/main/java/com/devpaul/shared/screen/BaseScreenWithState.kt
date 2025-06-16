package com.devpaul.shared.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
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
    navController: NavHostController,
    onInit: ((uiState: UiState, onIntent: (UiIntent) -> Unit) -> Unit)? = null,
    onUiEvent: suspend (UiEvent, (String) -> Unit) -> Unit = { _, _ -> },
    onDefaultError: (Defaults<Nothing>, (String) -> Unit) -> Unit = { _, _ -> },
    observeBackKeys: List<String>? = null,
    onBackResults: ((results: Map<String, Any?>, uiState: UiState, onIntent: (UiIntent) -> Unit) -> Unit)? = null,
    onBackPressed: ((onBackWithResult: (String, Any) -> Unit) -> Unit)? = null,
    content: @Composable (
        padding: PaddingValues,
        uiState: UiState,
        onIntent: (UiIntent) -> Unit,
        showSnackBar: (String) -> Unit,
        onBackWithResult: (String, Any) -> Unit
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

    val onBackWithResult: (String, Any) -> Unit = { key, value ->
        navController.previousBackStackEntry?.savedStateHandle?.set(key, value)
        navController.popBackStack()
    }

    onBackPressed?.let { handler ->
        BackHandler {
            handler(onBackWithResult)
        }
    }

    LaunchedEffect(Unit) {
        onInit?.let {
            withContext(Dispatchers.IO) {
                it(uiState, executeIntent)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            launch {
                onUiEvent(event, showSnackBar)
            }
        }
    }

    val backResults: Map<String, Any?> = observeBackKeys
        ?.associateWith { key ->
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.getLiveData<Any>(key)
                ?.observeAsState()
                ?.value
        }
        ?.filterValues { it != null }
        .orEmpty()

    LaunchedEffect(backResults) {
        if (backResults.isNotEmpty()) {
            observeBackKeys?.forEach { key ->
                navController.currentBackStackEntry?.savedStateHandle?.remove<Any>(key)
            }
            onBackResults?.invoke(backResults, uiState, executeIntent)
        }
    }

    Scaffold(
        snackbarHost = { CenteredSnackBarHost(hostState = snackBarHostState) },
        containerColor = Color.Transparent
    ) { padding ->
        content(padding, uiState, executeIntent, showSnackBar, onBackWithResult)
    }
}