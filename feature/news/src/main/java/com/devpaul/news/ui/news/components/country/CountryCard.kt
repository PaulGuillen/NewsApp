package com.devpaul.news.ui.news.components.country

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.GreenLight
import com.devpaul.core_platform.theme.White
import com.devpaul.news.domain.entity.CountryItemEntity

@Composable
fun CountryCard(
    country: CountryItemEntity,
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {

    val isDarkMode = isSystemInDarkTheme()

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) GreenLight else MaterialTheme.colorScheme.surface,
        ),
        onClick = {
            onClick()
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = rememberAsyncImagePainter(country.imageUrl),
                contentDescription = country.title,
                modifier = Modifier
                    .size(60.dp)
            )
            Text(
                textAlign = TextAlign.Center,
                text = country.title,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isDarkMode) {
                    if (isSelected) Black else White
                } else {
                    Black
                },
            )
        }
    }
}