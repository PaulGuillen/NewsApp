package com.devpaul.shared.ui.components.organisms.coachmark

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.theme.BrandAccent
import com.devpaul.core_platform.theme.EmergencyRed

@Composable
fun CoachMarkOverlay(
    target: LayoutCoordinates,
    title: String,
    description: String,
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "coachmark_pulse")
    val pulseProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_progress"
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { alpha = 0.99f }
    ) {
        drawRect(Color.Black.copy(alpha = 0.7f))
        val rect = target.boundsInRoot()
        val basePadding = 8.dp.toPx()
        val animatedPadding = basePadding + (pulseProgress * 16.dp.toPx())
        val pulseAlpha = 0.4f * (1f - pulseProgress)

        drawRoundRect(
            color = Color.White.copy(alpha = pulseAlpha),
            topLeft = Offset(
                x = rect.topLeft.x - animatedPadding,
                y = rect.topLeft.y - animatedPadding
            ),
            size = Size(
                width = rect.size.width + animatedPadding * 2,
                height = rect.size.height + animatedPadding * 2
            ),
            cornerRadius = CornerRadius(
                x = 24f + animatedPadding,
                y = 24f + animatedPadding
            ),
            style = Stroke(width = 2.dp.toPx())
        )
        drawRoundRect(
            color = Color.Transparent,
            topLeft = rect.topLeft,
            size = rect.size,
            cornerRadius = CornerRadius(24f),
            blendMode = BlendMode.Clear
        )
    }

    val isDark = isSystemInDarkTheme()
    // Neon style similar to EmergencyCard
    val containerBrush = if (isDark) {
        androidx.compose.ui.graphics.Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF052A2E),
                Color(0xFF063C42)
            )
        )
    } else null

    val containerColor = if (isDark) Color.Transparent else MaterialTheme.colorScheme.surface

    val borderColor = if (isDark) {
        BrandAccent.copy(alpha = 0.5f)
    } else {
        EmergencyRed.copy(alpha = 0.3f)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = containerColor),
            border = BorderStroke(1.dp, borderColor)
        ) {
            Box(modifier = if (isDark) Modifier.background(containerBrush!!) else Modifier) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = if (isDark) BrandAccent else EmergencyRed
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isDark) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onSkip) {
                            Text(text = "Saltar", color = if (isDark) Color.White.copy(alpha = 0.9f) else MaterialTheme.colorScheme.onSurface)
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = onNext,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isDark) BrandAccent else EmergencyRed,
                                contentColor = if (isDark) Color.Black else Color.White
                            )
                        ) {
                            Text(text = "Siguiente")
                        }
                    }
                }
            }
        }
    }
}