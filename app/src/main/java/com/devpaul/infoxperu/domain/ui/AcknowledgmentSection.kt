package com.devpaul.infoxperu.domain.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.devpaul.infoxperu.domain.models.res.Gratitude

import coil.compose.rememberAsyncImagePainter
import com.devpaul.infoxperu.ui.theme.BackgroundBlack
import timber.log.Timber

@Composable
fun AcknowledgmentSection(gratitude: List<Gratitude>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colorScheme.background)
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 10.dp)
    ) {
        gratitude.forEach { gratitudeR ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(360.dp)
                    .height(200.dp)
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Timber.d("Image: ${gratitudeR.image}")
                Image(
                    painter = rememberAsyncImagePainter(gratitudeR.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackgroundBlack.copy(alpha = 0.5f))
                )
                Text(
                    text = gratitudeR.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(16.dp),
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AcknowledgmentSectionPreview() {
    AcknowledgmentSection(
        gratitude = listOf(
            Gratitude(
                title = "Gracias",
                image = "https://www.google.com"
            ),
            Gratitude(
                title = "Gracias",
                image = "https://www.google.com"
            ),
        )
    )
}
