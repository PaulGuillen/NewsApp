package com.devpaul.news.ui.news.components.news_section

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SourceFilterChip(
    text: String,
    isSelected: Boolean,
    primary: Color,
    soft: Color,
    onClick: () -> Unit
) {

    val background by animateColorAsState(
        targetValue = if (isSelected) primary else soft,
        label = "chip-bg"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else primary,
        label = "chip-text"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(background)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp
        )
    }
}