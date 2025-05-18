package com.devpaul.util.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.devpaul.util.ui.extension.CenteredSnackBarHost
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
        snackbarHost = { CenteredSnackBarHost(hostState = snackBarHostState) },
        containerColor = Color.Transparent
    ) { paddingValues ->
        content(paddingValues, showSnackBar)
    }
}