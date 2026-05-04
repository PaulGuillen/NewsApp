package com.devpaul.home.ui.home.components.storysection

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class SeasonCardUi(
    val name: String,
    val icon: ImageVector,
    val gradientColors: List<Color>,
    val startText: String,
    val endText: String,
    val scheduleText: String,
    val isCurrent: Boolean,
)