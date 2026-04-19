package com.example.mylist.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewsSavedCard(
    title: String,
    category: String,
    time: String,
    isHighlighted: Boolean = false
) {

    val isDark = isSystemInDarkTheme()

    val cardColor = when {
        isHighlighted -> Color(0xFF3B82F6)
        isDark -> Color(0xFF1E293B)
        else -> Color.White
    }

    val textPrimary = if (isHighlighted) Color.White
    else if (isDark) Color.White else Color.Black

    val textSecondary = if (isHighlighted) Color.White.copy(alpha = 0.8f)
    else if (isDark) Color(0xFF94A3B8)
    else Color(0xFF6B7280)

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = category,
                fontSize = 10.sp,
                color = textSecondary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                color = textPrimary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = time,
                    fontSize = 10.sp,
                    color = textSecondary
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "SAVED",
                    fontSize = 10.sp,
                    color = textSecondary
                )
            }
        }
    }
}