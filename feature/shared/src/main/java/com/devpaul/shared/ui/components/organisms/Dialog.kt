package com.devpaul.shared.ui.components.organisms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devpaul.core_platform.theme.BrickRed

@Composable
fun DialogCard(
    message: String,
    title: String = "Ocurrió un problema",
    confirmText: String = "Entendido",
    onConfirm: (() -> Unit)? = null,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(6.dp),
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, BrickRed),
                    onClick = {
                        onConfirm?.invoke()
                        onDismiss()
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(4.dp),
                        text = confirmText
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogCardPreview() {
    MaterialTheme {
        DialogCard(
            title = "Ocurrió un problema",
            message = "Para continuar con la operación, solicita una nueva clave por SMS.",
            confirmText = "Entendido",
            onConfirm = {},
            onDismiss = {}
        )
    }
}