package com.devpaul.home.ui.home.components.emergency

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.theme.BrandAccent
import com.devpaul.core_platform.theme.EmergencyRed
import com.devpaul.core_platform.theme.InfoXPeruTheme

@Composable
fun EmergencyCard(onClick: () -> Unit) {

    val isDark = isSystemInDarkTheme()

    val containerBrush = if (isDark) {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF052A2E),
                Color(0xFF063C42)
            )
        )
    } else null

    val containerColor = if (isDark) Color.Transparent else Color.White

    val borderColor = if (isDark) {
        BrandAccent.copy(alpha = 0.5f)
    } else {
        EmergencyRed.copy(alpha = 0.3f)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(20.dp),
        onClick = onClick
    ) {

        Box(
            modifier = if (isDark)
                Modifier.background(containerBrush!!)
            else Modifier
        ) {

            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // ICON BOX
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isDark) BrandAccent else EmergencyRed
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = null,
                        tint = if (isDark) Color.Black else Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = if (isDark) "Central de Emergencias"
                        else "Números de Emergencia",
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isDark) BrandAccent else EmergencyRed
                    )

                    Text(
                        text = if (isDark)
                            "Policía, SAMU y Bomberos"
                        else
                            "PNP, SAMU y Bomberos",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isDark)
                            Color.White.copy(alpha = 0.7f)
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDark) BrandAccent else EmergencyRed
                    )
                ) {
                    Text(
                        "LLAMAR",
                        color = if (isDark) Color.Black else Color.White
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun EmergencyCardPreview() {
    InfoXPeruTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        EmergencyCard(onClick = {})
    }
}