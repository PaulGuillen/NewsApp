package com.devpaul.infoxperu.domain.ui

import android.service.autofill.OnClickAction
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomDialog(
    image: Painter?,
    title: String?,
    description: String?,
    buttonText: String?,
    onButtonClick: () -> Unit
) {
    Dialog(onDismissRequest = { /* Dialog no cancelable, no hacer nada en dismiss */ }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                image?.let {
                    Image(painter = it, contentDescription = null, modifier = Modifier.size(100.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                }
                title?.takeIf { it.isNotBlank() }?.let {
                    Text(text = it, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                description?.takeIf { it.isNotBlank() }?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                buttonText?.takeIf { it.isNotBlank() }?.let {
                    Button(onClick = onButtonClick) {
                        Text(text = it)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomDialogPreview() {
    CustomDialog(
        image = painterResource(id = android.R.drawable.ic_dialog_alert),
        title = "Title",
        description = "Description",
        buttonText = "Button",
        onButtonClick = { /* No hacer nada en preview */ }
    )
}
