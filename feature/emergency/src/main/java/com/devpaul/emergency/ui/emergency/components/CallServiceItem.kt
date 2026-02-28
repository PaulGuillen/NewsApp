package com.devpaul.emergency.ui.emergency.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.theme.Blue500
import com.devpaul.core_platform.theme.BrandAccent
import com.devpaul.core_platform.theme.White

@Composable
fun CriticalServiceItem(
    title: String,
    number: String,
    numberColor: Color,
    iconBackgroundColor: Color,
    iconTint: Color,
    onClick: () -> Unit = {}
) {

    val isDark = isSystemInDarkTheme()

    val darkBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF0A1A24),
            Color(0xFF0E2432)
        )
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = if (isDark) 0.dp else 4.dp,
        color = if (isDark) Color.Transparent else MaterialTheme.colorScheme.surface,
        border = if (isDark)
            BorderStroke(1.dp, BrandAccent.copy(alpha = 0.4f))
        else
            BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline)
    ) {

        Box(
            modifier = if (isDark)
                Modifier.background(darkBrush)
            else Modifier
        ) {

            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // ICON BOX
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isDark) BrandAccent.copy(alpha = 0.15f)
                            else iconBackgroundColor
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = null,
                        tint = if (isDark) BrandAccent else iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isDark) Color.White else MaterialTheme.colorScheme.tertiaryContainer
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = number,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) BrandAccent else numberColor
                    )
                }

                ServiceCallButton(
                    onClick = onClick,
                    dayColor = iconTint,
                    nightColor = BrandAccent
                )
            }
        }
    }
}