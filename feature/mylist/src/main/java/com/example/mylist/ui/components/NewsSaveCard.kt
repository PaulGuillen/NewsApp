package com.example.mylist.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpaul.core_platform.theme.BrandAccent
import com.devpaul.core_platform.theme.GreenDark

@Composable
fun NewsSavedCard(
    modifier: Modifier = Modifier,
    title: String,
    category: String,
    badgeLabel: String = category,
    badgeSource: String = badgeLabel,
    time: String,
    isHighlighted: Boolean = false,
    onClick: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    val isDark = isSystemInDarkTheme()

    val titleColor by animateColorAsState(
        targetValue = when {
            isHighlighted && isDark -> BrandAccent
            isHighlighted && !isDark -> GreenDark
            isDark -> Color.White
            else -> Color(0xFF0F172A)
        },
        label = "saved-title-color"
    )

    val metaColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
    val divider = if (isDark) Color(0xFF1E293B) else Color(0xFFE2E8F0)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        if (category.isNotBlank()) {
            Text(
                text = category.uppercase(),
                color = metaColor,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                lineHeight = 22.sp
            ),
            color = titleColor
        )

        if (badgeLabel.isNotBlank() || onDelete != null) {
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (badgeLabel.isNotBlank()) {
                    SavedSourceBadge(
                        label = badgeLabel,
                        source = badgeSource
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                if (onDelete != null) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Eliminar noticia",
                        tint = metaColor,
                        modifier = Modifier
                            .size(18.dp)
                            .clickable { onDelete() }
                    )
                }
            }
        }
    }

    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 0.6.dp,
        color = divider
    )
}

@Composable
private fun SavedSourceBadge(label: String, source: String) {
    val normalizedSource = source.trim()
    val color = when {
        normalizedSource.equals("Google", ignoreCase = true) ||
            normalizedSource.contains("google", ignoreCase = true) -> Color(0xFF2563EB)

        normalizedSource.equals("Reddit", ignoreCase = true) ||
            normalizedSource.contains("reddit", ignoreCase = true) -> Color(0xFFF97316)

        normalizedSource.equals("Delta Project", ignoreCase = true) ||
            normalizedSource.equals("Delta", ignoreCase = true) ||
            normalizedSource.contains("delta", ignoreCase = true) -> Color(0xFFE11D48)

        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = label,
            color = color,
            fontSize = 10.sp
        )
    }
}
