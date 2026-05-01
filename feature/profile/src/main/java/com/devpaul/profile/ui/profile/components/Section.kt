package com.devpaul.profile.ui.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Section(
    title: String,
    cardColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {

        Text(
            text = title,
            fontSize = 11.sp,
            color = Color(0xFF9CA3AF),
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Column(
            modifier = Modifier
                .background(cardColor, RoundedCornerShape(16.dp))
                .fillMaxWidth()
        ) {
            content()
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}