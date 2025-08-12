package com.devpaul.emergency.ui.emergency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.emergency.domain.entity.SectionItemEntity
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType
import com.devpaul.shared.ui.components.molecules.BottomNavigationBar
import com.devpaul.shared.ui.components.molecules.TopBarPrincipal
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun EmergencyScreen(
    navHostController: NavHostController
) {

    val viewModel: EmergencyViewModel = koinViewModel()

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navHostController,
        onUiEvent = { event, _ ->
            when (event) {
                is EmergencyUiEvent.NavigateToDetails -> {
                    navHostController.navigate(
                        Screen.EmergencyDetail.createRoute(
                            type = event.type
                        )
                    )
                }
            }
        },
    ) { _, uiState, onIntent, _, _ ->
        BaseContentLayout(
            isBodyScrollable = false,
            header = {
                TopBarPrincipal(
                    style = 3,
                    title = stringResource(R.string.header_available_sections)
                )
            },
            body = {
                EmergencyBody(
                    navController = navHostController,
                    uiState = uiState,
                    onIntent = onIntent,
                )
            },
            footer = {
                BottomNavigationBar(navHostController)
            },
        )
    }
}

@Composable
fun EmergencyBody(
    navController: NavHostController,
    uiState: EmergencyUiState,
    onIntent: (EmergencyUiIntent) -> Unit,
) {
    when (val state = uiState.section) {
        is ResultState.Loading -> {
            SkeletonRenderer(SkeletonType.SECTION_EMERGENCY)
        }

        is ResultState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.message,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(12.dp))
                Button(onClick = {
                    onIntent(EmergencyUiIntent.GetEmergencyServices)
                }) {
                    Text("Reintentar")
                }
            }
        }

        is ResultState.Success -> {
            SectionList(
                onIntent = onIntent,
                items = state.response.data
            )
        }

        ResultState.Idle -> {}
    }
}

@Composable
private fun SectionList(
    onIntent: (EmergencyUiIntent) -> Unit,
    items: List<SectionItemEntity>,
) {
    val priority = mapOf(
        "general" to 0,
        "local_security" to 1,
        "police" to 2,
        "firefighter" to 3,
        "civil_defense" to 4
    )

    val sorted = remember(items) { items.sortedBy { priority[it.type] ?: Int.MAX_VALUE } }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sorted, key = { it.type }) { item ->
            SectionCard(
                title = item.title,
                imageUrl = item.image,
                onClick = {
                    onIntent(
                        EmergencyUiIntent.NavigateToDetails(
                            type = item.type
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {

            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.0f),
                                Color.Black.copy(alpha = 0.55f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}