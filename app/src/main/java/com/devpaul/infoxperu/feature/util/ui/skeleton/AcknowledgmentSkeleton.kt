package com.devpaul.infoxperu.feature.util.ui.skeleton

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AcknowledgmentSkeleton() {
    val shimmerColor = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition(label = "")
    val color by transition.animateColor(
        initialValue = shimmerColor[0],
        targetValue = shimmerColor[1],
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 2.dp, bottom = 2.dp, end = 2.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        repeat(3) {
            Card(
                modifier = Modifier
                    .width(320.dp)
                    .height(220.dp)
                    .padding(10.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SkeletonScreenAcknowledgmentPreview() {
    AcknowledgmentSkeleton()
}
