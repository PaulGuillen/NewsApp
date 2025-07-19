package com.devpaul.home.ui.home.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.devpaul.core_platform.theme.White
import com.devpaul.home.data.datasource.mock.GratitudeMock
import com.devpaul.home.domain.entity.GratitudeDataEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType

@Composable
fun AcknowledgmentSection(
    modifier: Modifier = Modifier,
    context: Context,
    gratitudeState: ResultState<GratitudeEntity>,
    onRetry: () -> Unit,
) {
    when (gratitudeState) {
        is ResultState.Loading -> {
            SkeletonRenderer(type = SkeletonType.ACKNOWLEDGEMENT)
        }

        is ResultState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(gratitudeState.response.data) { gratitude ->
                    GratitudeCard(
                        gratitude = gratitude,
                        context = context,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }

        is ResultState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(top = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .width(320.dp)
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
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
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        )

                        Button(
                            onClick = onRetry,
                            shape = RectangleShape,
                            elevation = ButtonDefaults.buttonElevation(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
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

        else -> Unit
    }
}

@Composable
fun GratitudeCard(
    gratitude: GratitudeDataEntity,
    context: Context,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(vertical = 10.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, gratitude.link.toUri())
                context.startActivity(intent)
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(gratitude.imageUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gratitude.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
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