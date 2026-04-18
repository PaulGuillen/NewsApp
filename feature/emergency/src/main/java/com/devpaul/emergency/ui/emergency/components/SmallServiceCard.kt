package com.devpaul.emergency.ui.emergency.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SmallServiceCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    iconBackground: Color,
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

                // 🔹 ICON BOX
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(
                            if (isDark)
                                iconTint.copy(alpha = 0.15f) // fondo con el mismo color suavizado
                            else
                                iconBackground,
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isDark)
                            iconTint.copy(alpha = 0.9f) // mantiene color pero más suave
                        else
                            iconTint,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 🔹 TITLE
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isDark) Color.White
                    else MaterialTheme.colorScheme.tertiaryContainer
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 🔹 SUBTITLE
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isDark)
                        Color.White.copy(alpha = 0.7f)
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 BUTTON STYLES
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
                    shape = RoundedCornerShape(30.dp),
                    border = BorderStroke(1.dp, borderColor),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = containerColor,
                        contentColor = contentColor
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text(
                        text = "LLAMAR",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}