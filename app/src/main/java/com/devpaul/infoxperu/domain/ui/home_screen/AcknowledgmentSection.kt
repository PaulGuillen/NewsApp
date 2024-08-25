package com.devpaul.infoxperu.domain.ui.home_screen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.ui.skeleton.AcknowledgmentSkeleton

@Composable
fun AcknowledgmentSection(gratitudeState: ResultState<List<Gratitude>>, context: Context) {

    var showSkeleton by remember { mutableStateOf(true) }

    LaunchedEffect(gratitudeState) {
        showSkeleton = gratitudeState is ResultState.Loading
    }

    if (showSkeleton) {
        AcknowledgmentSkeleton()
    } else {
        AcknowledgmentSectionContent(gratitudeState = gratitudeState, context = context)
    }
}

@Composable
fun AcknowledgmentSectionContent(gratitudeState: ResultState<List<Gratitude>>, context: Context) {
    when (gratitudeState) {
        is ResultState.Loading -> {
            AcknowledgmentSkeleton()
        }

        is ResultState.Success -> {
            if (gratitudeState.data.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 10.dp)
                ) {
                    gratitudeState.data.forEach { gratitude ->
                        Card(
                            modifier = Modifier
                                .width(320.dp)
                                .padding(8.dp)
                                .clickable {
                                    val intent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse(gratitude.url))
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
                                    painter = rememberAsyncImagePainter(gratitude.image),
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
                                    text = gratitude.title,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(end = 14.dp, top = 8.dp),
                                    maxLines = 1,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            } else {
                AcknowledgmentSkeleton()
            }
        }

        is ResultState.Error -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {
                repeat(3) {
                    Card(
                        modifier = Modifier
                            .width(320.dp)
                            .padding(8.dp),
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
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(BackgroundBlack.copy(alpha = 0.25f))
                            )
                            Text(
                                text = "Error al cargar los agradecimientos",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(end = 14.dp, top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AcknowledgmentSectionSuccessPreview() {
    val gratitudeState = ResultState.Success(
        listOf(
            Gratitude(
                title = "DePeru",
                image = "https://www.example.com/image.png",
                url = "https://www.deperu.com"
            ),
            Gratitude(
                title = "Agradecido",
                image = "https://www.example.com/image2.png",
                url = "https://www.agradecido.com"
            )
        )
    )
    AcknowledgmentSection(gratitudeState = gratitudeState, context = LocalContext.current)
}

@Preview(showBackground = true)
@Composable
fun AcknowledgmentSectionLoadingPreview() {
    val gratitudeState = ResultState.Loading
    AcknowledgmentSection(gratitudeState = gratitudeState, context = LocalContext.current)
}

@Preview(showBackground = true)
@Composable
fun AcknowledgmentSectionErrorPreview() {
    val gratitudeState = ResultState.Error(Exception("Failed to load data"))
    AcknowledgmentSection(gratitudeState = gratitudeState, context = LocalContext.current)
}
