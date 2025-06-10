package com.devpaul.news.ui.news.components.deltaproject

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.White
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.shared.ui.skeleton.RedditSkeleton

@Composable
fun GDELTCards(
    navController: NavController,
    context: Context,
    selectedCountry: CountryItemEntity,
    deltaProject: DeltaProjectEntity?,
    deltaProjectError: String? = null,
    deltaProjectLoading: Boolean = false,
) {

    if (deltaProjectLoading) {
        RedditSkeleton()
    } else {
        if (deltaProject != null && deltaProject.data.items.isNotEmpty()) {
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
                        color = Color.Black
                    )
                    Text(
                        text = "Ver Más",
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            navController.navigate(
                                Screen.NewsDetail.createRoute(
                                    newsType = "deltaProjectNews",
                                    country =  selectedCountry,
                                )
                            )
                        }
                    )
                }

                LazyRow(
                    modifier = Modifier
                ) {
                    items(deltaProject.data.items) { articleItem ->
                        GDELTCard(context = context, deltaProject = articleItem)
                    }
                }

                Text(
                    text = "Cantidad: ${deltaProject.data.totalItems}",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp, end = 8.dp),
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .width(260.dp)
                        .height(180.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    border = BorderStroke(0.8.dp, Color.Black),
                    colors = CardDefaults.cardColors(
                        containerColor = White,
                        contentColor = Black
                    ),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text(
                            text = deltaProjectError ?: "No hay noticias disponibles",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Black,
                        )
                    }
                }
            }
        }
    }
}