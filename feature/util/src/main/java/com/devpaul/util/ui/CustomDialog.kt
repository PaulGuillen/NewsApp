package com.devpaul.util.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.White

@Composable
fun CustomDialog(
    image: Painter?,
    title: String?,
    description: String?,
    buttonText: String?,
    onButtonClick: () -> Unit
) {
    Dialog(onDismissRequest = { }) {
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
                    Button(
                        onClick = onButtonClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrickRed,
                            contentColor = White
                        )
                    ) {
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
        image = painterResource(id = R.drawable.baseline_check_circle_24),
        title = "Title",
        description = "Description",
        buttonText = "Button",
        onButtonClick = { }
    )
}