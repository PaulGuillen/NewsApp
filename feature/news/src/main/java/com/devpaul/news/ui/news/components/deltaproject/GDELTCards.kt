package com.devpaul.news.ui.news.components.deltaproject

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.White
import com.devpaul.news.data.datasource.mock.NewsMock
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.domain.entity.DeltaProjectDataEntity
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType

@Composable
fun GDELTCards(
    navController: NavController,
    context: Context,
    deltaProjectState: ResultState<DeltaProjectDataEntity>,
    selectedCountry: CountryItemEntity,
    onRetry: () -> Unit,
) {

    when (deltaProjectState) {
        is ResultState.Loading -> {
            SkeletonRenderer(type = SkeletonType.GENERAL_NEWS)
        }

        is ResultState.Success -> {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GDELT Noticias",
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Ver Más",
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable {
                            navController.navigate(
                                Screen.NewsDetail.createRoute(
                                    newsType = "deltaProjectNews",
                                    country = selectedCountry,
                                )
                            )
                        }
                    )
                }

                LazyRow(
                    modifier = Modifier
                ) {
                    items(deltaProjectState.response.articles) { articleItem ->
                        GDELTCard(context = context, deltaProject = articleItem)
                    }
                }

                Text(
                    text = "Cantidad: ${deltaProjectState.response.articles.size}",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp, end = 8.dp),
                )
            }
        }

        is ResultState.Error -> {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = deltaProjectState.message,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )

                        Button(
                            onClick = onRetry,
                            shape = RectangleShape,
                            elevation = ButtonDefaults.buttonElevation(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = White
                            )
                        ) {
                            Text(text = "Reintentar", fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        else -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun GDELTCardsLoadingPreview() {
    val selectedCountry by remember { mutableStateOf<CountryItemEntity?>(null) }
    selectedCountry?.let {
        GDELTCards(
            navController = rememberNavController(),
            context = LocalContext.current,
            deltaProjectState = ResultState.Loading,
            selectedCountry = it,
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GDELTCardsSuccessPreview() {
    val selectedCountry by remember { mutableStateOf<CountryItemEntity?>(null) }
    selectedCountry?.let {
        GDELTCards(
            navController = rememberNavController(),
            context = LocalContext.current,
            deltaProjectState = ResultState.Success(NewsMock().deltaMock),
            selectedCountry = it,
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GDELTCardsErrorPreview() {
    val selectedCountry by remember { mutableStateOf<CountryItemEntity?>(null) }
    selectedCountry?.let {
        GDELTCards(
            navController = rememberNavController(),
            context = LocalContext.current,
            deltaProjectState = ResultState.Error("Error loading GDELT news"),
            selectedCountry = it,
            onRetry = {}
        )
    }
}