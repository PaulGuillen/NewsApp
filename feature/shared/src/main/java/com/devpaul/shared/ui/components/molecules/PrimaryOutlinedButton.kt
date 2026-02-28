package com.devpaul.shared.ui.components.molecules


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun PrimaryOutlinedButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    height: Dp = 52.dp,
    shape: Shape = RoundedCornerShape(30)
) {

    val isDark = isSystemInDarkTheme()

    val borderColor = if (isDark)
        Color(0xFF1F3A5F)
    else
        Color(0xFFB0C4DE)

    val containerColor = if (isDark)
        Color(0xFF0F1E33)
    else
        Color(0xFFF2F4F7)

    val contentColor = if (isDark)
        Color(0xFF4DA3FF)
    else
        Color(0xFF1565C0)

    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        shape = shape,
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}