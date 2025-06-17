package com.devpaul.home.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.BackgroundBlack
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.BlueDark
import com.devpaul.core_platform.theme.White
import com.devpaul.home.data.datasource.mock.GratitudeMock
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.shared.ui.components.atoms.skeleton.AcknowledgmentSkeleton

@Composable
fun AcknowledgmentSection(
    context: Context,
    gratitudeState: ResultState<GratitudeEntity>,
    onRetry: () -> Unit,
) {

    when (gratitudeState) {
        is ResultState.Loading -> {
            AcknowledgmentSkeleton()
        }

        is ResultState.Success -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {
                gratitudeState.response.data.forEach { gratitude ->
                    Card(
                        modifier = Modifier
                            .width(320.dp)
                            .padding(8.dp)
                            .clickable {
                                val intent =
                                    Intent(Intent.ACTION_VIEW, gratitude.link.toUri())
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
                                painter = rememberAsyncImagePainter(gratitude.imageUrl),
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
        }

        is ResultState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .width(320.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = gratitudeState.message,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Black,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )

                        Button(
                            onClick = onRetry,
                            shape = RectangleShape,
                            elevation = ButtonDefaults.buttonElevation(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BlueDark,
                                contentColor = White
                            ),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("Reintentar")
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
    AcknowledgmentSection(
        context = LocalContext.current,
        gratitudeState = ResultState.Success(GratitudeMock().gratitudeMock),
        onRetry = {}
    )
}

@Preview(showBackground = true)
@Composable
fun AcknowledgmentSectionLoadingPreview() {
    AcknowledgmentSection(
        context = LocalContext.current,
        gratitudeState = ResultState.Loading,
        onRetry = {}
    )
}

@Preview(showBackground = true)
@Composable
fun AcknowledgmentSectionErrorPreview() {
    AcknowledgmentSection(
        context = LocalContext.current,
        gratitudeState = ResultState.Error("Error al cargar los agradecimientos"),
        onRetry = {}
    )
}