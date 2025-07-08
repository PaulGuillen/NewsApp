package com.devpaul.shared.data.skeleton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SkeletonRenderer(
    type: SkeletonType,
    modifier: Modifier = Modifier
) {
    val strategy = SkeletonFactory.get(type)
    strategy.Render(modifier)
}