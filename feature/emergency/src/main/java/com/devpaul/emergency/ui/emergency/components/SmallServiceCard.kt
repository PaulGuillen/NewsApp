package com.devpaul.emergency.ui.emergency.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SmallServiceCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    index: Int = 0
) {

    val isDark = isSystemInDarkTheme()

    val featuredGradients = listOf(
        listOf(Color(0xFF1B263B), Color(0xFF274C77)),
        listOf(Color(0xFF14213D), Color(0xFF1D3557)),
        listOf(Color(0xFF1E3A5F), Color(0xFF1B4965)),
        listOf(Color(0xFF0F3057), Color(0xFF00587A)),
        listOf(Color(0xFF1F4068), Color(0xFF162447))
    )

    val gradient = featuredGradients[index % featuredGradients.size]

    val backgroundBrush = if (isDark) {
        Brush.verticalGradient(gradient)
    } else null

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = if (isDark) 0.dp else 4.dp,
        color = if (isDark) Color.Transparent else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = 0.5.dp,
            color = if (isDark)
                Color.White.copy(alpha = 0.08f)
            else
                MaterialTheme.colorScheme.outline
        )
    ) {

        Box(
            modifier = if (backgroundBrush != null)
                Modifier.background(backgroundBrush)
            else Modifier
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    color = if (isDark) Color.White
                    else MaterialTheme.colorScheme.tertiaryContainer
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isDark)
                        Color.White.copy(alpha = 0.7f)
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

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

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30),
                    border = BorderStroke(1.dp, borderColor),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = containerColor,
                        contentColor = contentColor
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text("Llamar")
                }
            }
        }
    }
}