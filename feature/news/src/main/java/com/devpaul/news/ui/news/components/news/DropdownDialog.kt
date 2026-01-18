package com.devpaul.news.ui.news.components.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropdownDialog(
    title: String,
    onDismiss: () -> Unit,
    onItemSelected: (String) -> Unit
) {

    val news = listOf(
        "Google News",
        "Reddit",
        "Delta Project",
    )

    AlertDialog(
        modifier = Modifier,
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                news.forEach { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemSelected(item)
                            }
                            .padding(vertical = 12.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        },
        confirmButton = {}
    )
}