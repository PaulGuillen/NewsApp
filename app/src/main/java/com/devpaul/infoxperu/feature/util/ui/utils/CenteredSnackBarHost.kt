package com.devpaul.infoxperu.feature.util.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CenteredSnackBarHost(hostState: SnackbarHostState) {
    SnackbarHost(hostState) { data ->
        Snackbar(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            content = {
                Text(
                    text = data.visuals.message,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}

@Preview
@Composable
fun CenteredSnackBarHostPreview() {
    val snackBarHostState = remember { SnackbarHostState() }
    CenteredSnackBarHost(snackBarHostState)
}
