package com.devpaul.infographic.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.devpaul.core_platform.theme.BrandAccent
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.ColorHorizontalDivider
import com.devpaul.core_platform.theme.GrayBlack
import com.devpaul.core_platform.theme.GreenDark
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.core_platform.theme.SlateGray
import com.devpaul.core_platform.theme.Taupe
import com.devpaul.core_platform.theme.TextSecondary
import com.devpaul.core_platform.theme.White

@Composable
fun ForceUpdateScreen(onOpenStore: () -> Unit) {
    val isDark = isSystemInDarkTheme()
    val colors = rememberForceUpdateColors(isDark)

    val transition = rememberInfiniteTransition(label = "pulse")
    val dotScale by transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, colors.border, RoundedCornerShape(24.dp))
                .background(colors.card)
        ) {
            ForceUpdateHeader(colors, dotScale)
            ForceUpdateBody(colors, isDark, onOpenStore)
        }
    }
}

@Composable
private fun ForceUpdateHeader(
    colors: ForceUpdateColors,
    dotScale: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.header)
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ForceUpdateBadge(colors, dotScale)

        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(colors.iconBackground)
                .border(1.5.dp, colors.iconBorder, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Update,
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(38.dp)
            )
        }
    }
}

@Composable
private fun ForceUpdateBadge(
    colors: ForceUpdateColors,
    dotScale: Float
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(White.copy(alpha = 0.12f))
            .border(1.dp, White.copy(alpha = 0.22f), CircleShape)
            .padding(horizontal = 14.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .scale(dotScale)
                .clip(CircleShape)
                .background(colors.pulse)
        )

        Text(
            text = "NUEVA VERSIÓN DISPONIBLE",
            color = White.copy(alpha = 0.84f),
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.6.sp
        )
    }
}

@Composable
private fun ForceUpdateBody(
    colors: ForceUpdateColors,
    isDark: Boolean,
    onOpenStore: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Actualiza para continuar",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colors.title,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = "Esta versión ya no está soportada. Descarga la más reciente para seguir usando la app.",
            style = MaterialTheme.typography.bodySmall,
            color = colors.description,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(18.dp))

        HorizontalDivider(color = colors.divider)

        Spacer(Modifier.height(16.dp))

        ForceUpdateFeature(Icons.Default.Security, "Correcciones de seguridad", colors, isDark)
        ForceUpdateFeature(Icons.Default.Bolt, "Mejoras de rendimiento", colors, isDark)
        ForceUpdateFeature(Icons.Default.NewReleases, "Nuevas funcionalidades", colors, isDark)

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onOpenStore,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.accent,
                contentColor = if (isDark) Color.Black else Color.White
            )
        ) {
            Text(
                text = "Actualizar en Play Store",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = "La app no funcionará sin actualizar",
            style = MaterialTheme.typography.labelSmall,
            color = colors.caption,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ForceUpdateFeature(
    icon: ImageVector,
    text: String,
    colors: ForceUpdateColors,
    isDark: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colors.accent.copy(alpha = if (isDark) 0.16f else 0.10f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.accent,
                modifier = Modifier.size(17.dp)
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = colors.featureText
        )
    }
}

@Composable
private fun rememberForceUpdateColors(isDark: Boolean): ForceUpdateColors {
    return if (isDark) {
        ForceUpdateColors(
            background = Color(0xFF031316),
            card = Color(0xFF052A2E),
            header = Color(0xFF063C42),
            accent = BrandAccent,
            title = White,
            description = White.copy(alpha = 0.68f),
            featureText = White.copy(alpha = 0.82f),
            caption = White.copy(alpha = 0.45f),
            border = BrandAccent.copy(alpha = 0.45f),
            divider = BrandAccent.copy(alpha = 0.14f),
            iconBackground = BrandAccent.copy(alpha = 0.14f),
            iconBorder = BrandAccent.copy(alpha = 0.30f),
            pulse = GreenDark
        )
    } else {
        ForceUpdateColors(
            background = Taupe,
            card = White,
            header = BrickRed,
            accent = BrickRed,
            title = TextSecondary,
            description = SlateGray,
            featureText = TextSecondary.copy(alpha = 0.78f),
            caption = GrayBlack,
            border = BrickRed.copy(alpha = 0.25f),
            divider = ColorHorizontalDivider,
            iconBackground = White.copy(alpha = 0.18f),
            iconBorder = White.copy(alpha = 0.28f),
            pulse = GreenDark
        )
    }
}

@Composable
fun ForceUpdateScreenDefault() {
    val context = LocalContext.current
    ForceUpdateScreen { openPlayStore(context) }
}

fun openPlayStore(context: Context) {
    val appPackageName = context.packageName

    try {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, "market://details?id=$appPackageName".toUri())
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    } catch (_: Exception) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                "https://play.google.com/store/apps/details?id=$appPackageName".toUri()
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
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
    InfoXPeruTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        ForceUpdateScreenDefault()
    }
}