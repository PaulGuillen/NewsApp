package com.devpaul.shared.ui.components.atoms.base.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.ProfileDarkMuted
import com.devpaul.core_platform.theme.ProfileDarkText
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
    val isDark = isSystemInDarkTheme()
    val scheme = MaterialTheme.colorScheme

    return remember(isDark, scheme, accent) {
        if (isDark) {
            NotificationPalette(
                shellBackground = Black.copy(alpha = 0.72f),
                cardBackground = Color(0xFF101A26),
                cardBorder = accent.copy(alpha = 0.38f),
                headerBackground = Brush.horizontalGradient(
                    listOf(
                        accent.copy(alpha = 0.18f),
                        Color(0xFF16283D),
                        Color(0xFF101A26)
                    )
                ),
                headerText = accent,
                titleText = ProfileDarkText,
                messageText = Color(0xFFB9C6D8),
                iconBadgeBackground = accent.copy(alpha = 0.18f),
                iconTint = accent,
                primaryButton = accent,
                onPrimaryButton = Black,
                secondaryButtonText = ProfileDarkText,
                secondaryButtonBorder = accent.copy(alpha = 0.32f),
                closeTint = ProfileDarkMuted,
                divider = White.copy(alpha = 0.08f),
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
                        White,
                        White
                    )
                ),
                headerText = accent,
                titleText = Color(0xFF18212F),
                messageText = Black.copy(alpha = 0.74f),
                iconBadgeBackground = accent.copy(alpha = 0.12f),
                iconTint = accent,
                primaryButton = accent,
                onPrimaryButton = White,
                secondaryButtonText = Color(0xFF2C3A4D),
                secondaryButtonBorder = accent.copy(alpha = 0.22f),
                closeTint = Color(0xFF6A7688),
                divider = accent.copy(alpha = 0.08f),
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
