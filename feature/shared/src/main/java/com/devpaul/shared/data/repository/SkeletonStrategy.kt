package com.devpaul.shared.data.repository

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface SkeletonStrategy {
    @Composable
    fun Render(modifier: Modifier)
}