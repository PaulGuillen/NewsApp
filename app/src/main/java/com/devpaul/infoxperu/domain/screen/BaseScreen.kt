package com.devpaul.infoxperu.domain.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
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