package com.devpaul.infoxperu.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Update
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.devpaul.core_platform.theme.BrandAccent
import com.devpaul.core_platform.theme.EmergencyRed
import com.devpaul.core_platform.theme.InfoXPeruTheme

@Composable
fun ForceUpdateScreen(onOpenStore: () -> Unit) {
    val isDark = isSystemInDarkTheme()

    val containerBrush = if (isDark) {
        Brush.horizontalGradient(colors = listOf(Color(0xFF052A2E), Color(0xFF063C42)))
    } else null

    val containerColor = if (isDark) Color.Transparent else MaterialTheme.colorScheme.surface

    val borderColor =
        if (isDark) BrandAccent.copy(alpha = 0.6f) else EmergencyRed.copy(alpha = 0.35f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = containerColor),
            border = BorderStroke(1.dp, borderColor)
        ) {
            Column(modifier = if (isDark) Modifier.background(containerBrush!!) else Modifier) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Image on top (maintenance)
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = if (isDark) BrandAccent else EmergencyRed),
                        modifier = Modifier.size(96.dp)
                    ) {
                        // centered update icon, always white
                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(96.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Update,
                                contentDescription = "Update",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    // Texts
                    Column(
                        modifier = Modifier.padding(top = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Nueva versión disponible",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isDark) BrandAccent else EmergencyRed
                        )

                        Text(
                            text = "Actualiza la aplicación para continuar",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isDark) Color.White.copy(alpha = 0.85f) else MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.9f
                            ),
                            modifier = Modifier.padding(top = 6.dp, bottom = 12.dp)
                        )
                    }

                    // Button
                    Button(
                        onClick = onOpenStore,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDark) BrandAccent else EmergencyRed,
                            contentColor = if (isDark) Color.Black else Color.White
                        ),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(text = "Actualizar")
                    }
                }
            }
        }
    }
}

@Composable
fun ForceUpdateScreenDefault() {
    val ctx = LocalContext.current
    ForceUpdateScreen(onOpenStore = { openPlayStore(ctx) })
}

fun openPlayStore(context: Context) {
    val appPackageName = context.packageName
    try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                "market://details?id=$appPackageName".toUri()
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
    } catch (_: Exception) {
        // fallback to web
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                "https://play.google.com/store/apps/details?id=$appPackageName".toUri()
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
    }
}

@Preview(
    name = "ForceUpdate - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "ForceUpdate - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ForceUpdatePreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        ForceUpdateScreenDefault()
    }
}

