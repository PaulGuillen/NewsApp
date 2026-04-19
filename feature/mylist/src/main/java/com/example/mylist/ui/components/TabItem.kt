package com.example.mylist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val isDark = isSystemInDarkTheme()

    val bg = if (isSelected)
        if (isDark) Color(0xFF1D4ED8) else Color(0xFF3B82F6)
    else
        Color.Transparent

    val textColor = if (isSelected)
        Color.White
    else
        if (isDark) Color(0xFF94A3B8) else Color(0xFF6B7280)

    Box(
        modifier = Modifier
            .background(bg, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(title, color = textColor, fontSize = 12.sp)
    }
}