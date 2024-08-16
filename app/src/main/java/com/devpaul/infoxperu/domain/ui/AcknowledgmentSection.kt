package com.devpaul.infoxperu.domain.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

@Composable
fun AcknowledgmentSection(gratitude: List<Gratitude>, context: Context) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 10.dp)
    ) {
        gratitude.forEach { gratitudeR ->
            Card(
                modifier = Modifier
                    .width(320.dp)
                    .padding(8.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(gratitudeR.url))
                        context.startActivity(intent)
                    },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(gratitudeR.image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)),
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
                        fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 8.dp, top = 8.dp),
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                }

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
                title = "DePeru",
                image = "https://www.example.com/image.png",
                url = "https://www.deperu.com"
            ),
            Gratitude(
                title = "Agradecido",
                image = "https://www.example.com/image2.png",
                url = "https://www.agradecido.com"
            ),
        ),
        context = LocalContext.current
    )
}
