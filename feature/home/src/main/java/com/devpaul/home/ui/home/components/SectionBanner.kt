package com.devpaul.home.ui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.PinkGray
import com.devpaul.home.data.datasource.mock.SectionMock
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType

@Composable
fun SectionBanner(
    navHostController: NavHostController,
    sectionState: ResultState<SectionEntity>,
    onRetryClick: (() -> Unit)? = null,
) {
    when (sectionState) {
        is ResultState.Loading -> {
            SkeletonRenderer(type = SkeletonType.SECTION_BANNER)
        }

        is ResultState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PinkGray)
                    .padding(vertical = 10.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_world),
                    contentDescription = "Mundo",
                    modifier = Modifier
                        .size(80.dp)
                        .offset { IntOffset(0, 0) }
                        .zIndex(1f)
                )

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    shadowElevation = 2.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = { onRetryClick?.invoke() }) {
                            Text(text = "Reintentar")
                        }
                    }
                }
            }
        }

        is ResultState.Success -> {
            val sections = sectionState.response.data

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PinkGray)
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_world),
                    contentDescription = "Mundo",
                    modifier = Modifier
                        .size(80.dp)
                        .offset { IntOffset(0, 0) }
                        .zIndex(1f)
                )

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    shadowElevation = 2.dp
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            sections.take(3).forEach { section ->
                                SectionItem(
                                    imageUrl = section.imageUrl,
                                    label = section.title,
                                    onClick = {
                                        when (section.type.lowercase()) {
                                            "emergency" -> navHostController.navigate(Screen.Emergency.route)
                                            "news" -> navHostController.navigate(Screen.News.route)
                                            "suggestion" -> navHostController.navigate(Screen.Suggestions.route)
                                            else -> {
                                            }
                                        }
                                    }
                                )
                            }
                        }

                        HorizontalDivider(thickness = 1.5.dp, color = Color.LightGray)

                        Text(
                            text = "Secciones disponibles",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                }
            }
        }

        else -> {}
    }
}

@Composable
private fun SectionItem(
    imageUrl: String,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = label,
            modifier = Modifier
                .size(52.dp),
            contentScale = ContentScale.Crop,
        )

        Spacer(Modifier.height(4.dp))
        Text(text = label, style = MaterialTheme.typography.bodyMedium, fontSize = 14.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun SectionBannerPreview() {
    SectionBanner(
        navHostController = rememberNavController(),
        sectionState = ResultState.Success(SectionMock().sectionMock)
    )
}

@Preview(showBackground = true)
@Composable
fun SectionBannerErrorPreview() {
    SectionBanner(
        navHostController = rememberNavController(),
        sectionState = ResultState.Error("Error al cargar las secciones"),
        onRetryClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SectionBannerLoadingPreview() {
    SectionBanner(
        navHostController = rememberNavController(),
        sectionState = ResultState.Loading
    )
}