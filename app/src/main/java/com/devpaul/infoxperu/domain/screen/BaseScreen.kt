package com.devpaul.infoxperu.domain.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.devpaul.infoxperu.domain.ui.utils.CenteredSnackBarHost
import kotlinx.coroutines.launch

@Composable
fun BaseScreen(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (PaddingValues, (String) -> Unit) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val showSnackBar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { CenteredSnackBarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        content(paddingValues, showSnackBar)
    }

}