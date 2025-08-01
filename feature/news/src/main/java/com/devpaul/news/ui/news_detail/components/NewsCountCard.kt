package com.devpaul.news.ui.news_detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpaul.core_platform.theme.DarkChocolate
import com.devpaul.core_platform.theme.GreenDark
import com.devpaul.core_platform.theme.GreenLight
import com.devpaul.core_platform.theme.White

@Composable
fun NewsCountCard(title: String) {
    val isDarkMode = isSystemInDarkTheme()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors(containerColor = if (isDarkMode) DarkChocolate else GreenLight),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = if (isDarkMode) White else GreenDark,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCountCardPreview() {
    NewsCountCard(title = "Total News Articles: 100")
}