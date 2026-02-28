package com.devpaul.home.ui.home.components.storysection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StoriesSection() {
    val featuredGradients = listOf(
        listOf(Color(0xFF1E3A8A), Color(0xFF2563EB)),
        listOf(Color(0xFF1E40AF), Color(0xFF3B82F6)),
        listOf(Color(0xFF1D4ED8), Color(0xFF60A5FA)),
        listOf(Color(0xFF0F172A), Color(0xFF1E293B)),
        listOf(Color(0xFF0C4A6E), Color(0xFF0284C7)),
        listOf(Color(0xFF1E3A8A), Color(0xFF0EA5E9)),
        listOf(Color(0xFF1E293B), Color(0xFF2563EB)),
        listOf(Color(0xFF172554), Color(0xFF3B82F6)),
        listOf(Color(0xFF1E40AF), Color(0xFF38BDF8)),
        listOf(Color(0xFF1D4ED8), Color(0xFF1E3A8A))
    )
    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = Icons.Default.ShowChart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "Historias Destacadas",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }

            TextButton(onClick = {}) {
                Text("Ver todas")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) { index ->
                StoryCard(
                    gradientColors = featuredGradients[index % featuredGradients.size]
                )
            }
        }
    }
}