package com.devpaul.infoxperu.domain.ui.skeleton

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SkeletonGDELT() {
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

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(20.dp)
                    .background(color, RoundedCornerShape(4.dp))
            )

            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(20.dp)
                    .background(color, RoundedCornerShape(4.dp))
            )
        }

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
        ) {
            repeat(4) {
                Card(
                    modifier = Modifier
                        .width(300.dp)
                        .height(240.dp)
                        .padding(end = 18.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 18.dp)
                .width(90.dp)
                .height(20.dp)
                .background(color, RoundedCornerShape(4.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SkeletonGDELTPreview() {
    SkeletonGDELT()
}
