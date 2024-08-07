package com.devpaul.infoxperu.domain.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.devpaul.infoxperu.ui.theme.InfoXPeruTheme

@Composable
fun ScreenLoading() {
    Dialog(onDismissRequest = { /* No hacer nada, deshabilitar el dismiss */ }) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Cargando...",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ScreenLoadingPreview() {
    InfoXPeruTheme {
        ScreenLoading()
    }
}
