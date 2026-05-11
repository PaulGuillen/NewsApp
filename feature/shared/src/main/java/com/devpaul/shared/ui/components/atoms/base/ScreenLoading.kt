package com.devpaul.shared.ui.components.atoms.base

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.BrandAccent
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.core_platform.theme.ProfileDarkText
import com.devpaul.core_platform.theme.White

@Composable
fun ScreenLoading() {
    Dialog(onDismissRequest = {}) {
        ScreenLoadingContent()
    }
}

@Composable
private fun ScreenLoadingContent() {
    val isDark = isSystemInDarkTheme()
    val palette = remember(isDark) {
        if (isDark) {
            Triple(
                Color(0xAA050A11),
                Color(0xFF101A26),
                BrandAccent
            )
        } else {
            Triple(
                Black.copy(alpha = 0.26f),
                White,
                BrickRed
            )
        }
    }
    val cardColor = palette.second
    val accent = palette.third
    val textColor = if (isDark) ProfileDarkText else Color(0xFF2B2730)
    val shape = RoundedCornerShape(24.dp)

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .shadow(
                    elevation = 18.dp,
                    shape = shape,
                    ambientColor = accent.copy(alpha = if (isDark) 0.34f else 0.12f),
                    spotColor = accent.copy(alpha = if (isDark) 0.34f else 0.12f)
                )
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = shape
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(42.dp),
                    color = accent,
                    strokeWidth = 4.dp,
                    trackColor = accent.copy(alpha = if (isDark) 0.18f else 0.12f)
                )

                Spacer(modifier = Modifier.size(12.dp))

                Text(
                    text = "Cargando...",
                    color = textColor,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(
    name = "Screen Loading - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Screen Loading - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ScreenLoadingPreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        ScreenLoadingContent()
    }
}