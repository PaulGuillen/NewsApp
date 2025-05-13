package com.devpaul.util.screen.atomic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.theme.SlateGray

@Composable
fun DividerView() {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(0.6.dp)
            .background(SlateGray)
    )
}

@Preview(showBackground = true)
@Composable
fun DividerViewPreview() {
    DividerView()
}