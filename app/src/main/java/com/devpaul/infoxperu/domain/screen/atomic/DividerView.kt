package com.devpaul.infoxperu.domain.screen.atomic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.infoxperu.ui.theme.SlateGray

@Composable
fun DividerView() {
    Box(
        modifier = Modifier
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