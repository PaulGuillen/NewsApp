package com.devpaul.shared.ui.components.atoms.base.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.White

@Immutable
internal data class NotificationPalette(
    val shellBackground: Color,
    val cardBackground: Color,
    val cardBorder: Color,
    val headerBackground: Brush,
    val headerText: Color,
    val titleText: Color,
    val messageText: Color,
    val iconBadgeBackground: Color,
    val iconTint: Color,
    val primaryButton: Color,
    val onPrimaryButton: Color,
    val secondaryButtonText: Color,
    val secondaryButtonBorder: Color,
    val closeTint: Color,
    val divider: Color,
    val glow: Color,
)

@Composable
internal fun notificationPalette(accent: Color): NotificationPalette {
    val scheme = MaterialTheme.colorScheme
    val isDark = scheme.surface.luminance() < 0.5f
    val contentAccent = if (isDark) lerp(accent, White, 0.5f) else accent

    return remember(isDark, scheme, accent) {
        if (isDark) {
            NotificationPalette(
                shellBackground = Black.copy(alpha = 0.72f),
                cardBackground = scheme.surface,
                cardBorder = accent.copy(alpha = 0.38f),
                headerBackground = Brush.horizontalGradient(
                    listOf(
                        accent.copy(alpha = 0.18f),
                        scheme.surfaceVariant,
                        scheme.surface
                    )
                ),
                headerText = contentAccent,
                titleText = scheme.onSurface,
                messageText = scheme.onSurfaceVariant,
                iconBadgeBackground = accent.copy(alpha = 0.18f),
                iconTint = contentAccent,
                primaryButton = contentAccent,
                onPrimaryButton = Black,
                secondaryButtonText = scheme.onSurface,
                secondaryButtonBorder = accent.copy(alpha = 0.32f),
                closeTint = scheme.onSurfaceVariant,
                divider = scheme.outlineVariant,
                glow = accent.copy(alpha = 0.28f)
            )
        } else {
            NotificationPalette(
                shellBackground = Black.copy(alpha = 0.38f),
                cardBackground = scheme.surface,
                cardBorder = accent.copy(alpha = 0.18f),
                headerBackground = Brush.horizontalGradient(
                    listOf(
                        accent.copy(alpha = 0.15f),
                        scheme.surface,
                        scheme.surface
                    )
                ),
                headerText = accent,
                titleText = scheme.onSurface,
                messageText = scheme.onSurfaceVariant,
                iconBadgeBackground = accent.copy(alpha = 0.12f),
                iconTint = accent,
                primaryButton = accent,
                onPrimaryButton = White,
                secondaryButtonText = scheme.onSurface,
                secondaryButtonBorder = accent.copy(alpha = 0.22f),
                closeTint = scheme.onSurfaceVariant,
                divider = scheme.outlineVariant,
                glow = accent.copy(alpha = 0.10f)
            )
        }
    }
}

@Composable
internal fun NotificationContent(
    titleHeader: String,
    title: String,
    message: String,
    icon: ImageVector,
    primaryButtonText: String,
    accent: Color,
    onPrimaryClick: () -> Unit,
    onDismiss: (() -> Unit)? = null,
    secondaryButtonText: String? = null,
    onSecondaryClick: (() -> Unit)? = null,
    showDismissIcon: Boolean = true,
) {
    val palette = notificationPalette(accent)
    val shape = RoundedCornerShape(24.dp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 18.dp,
                shape = shape,
                ambientColor = palette.glow,
                spotColor = palette.glow
            ),
        shape = shape,
        border = BorderStroke(1.dp, palette.cardBorder),
        colors = CardDefaults.cardColors(containerColor = palette.cardBackground)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(palette.headerBackground)
                    .padding(start = 20.dp, end = 8.dp, top = 14.dp, bottom = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = titleHeader,
                    color = palette.headerText,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                if (showDismissIcon) {
                    IconButton(onClick = { onDismiss?.invoke() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = palette.closeTint
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }

            HorizontalDivider(color = palette.divider)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 22.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .background(palette.iconBadgeBackground, CircleShape)
                        .border(
                            width = 1.dp,
                            color = palette.cardBorder,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = palette.iconTint,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        color = palette.titleText,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = message,
                        color = palette.messageText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            HorizontalDivider(color = palette.divider)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (!secondaryButtonText.isNullOrBlank() && onSecondaryClick != null) {
                    OutlinedButton(
                        onClick = {
                            onSecondaryClick()
                            onDismiss?.invoke()
                        },
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, palette.secondaryButtonBorder),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = palette.secondaryButtonText
                        )
                    ) {
                        Text(secondaryButtonText)
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                }

                Button(
                    onClick = {
                        onPrimaryClick()
                        onDismiss?.invoke()
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = palette.primaryButton,
                        contentColor = palette.onPrimaryButton
                    )
                ) {
                    Text(
                        text = primaryButtonText,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
