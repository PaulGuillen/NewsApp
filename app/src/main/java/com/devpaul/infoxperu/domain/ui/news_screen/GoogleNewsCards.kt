package com.devpaul.infoxperu.domain.ui.news_screen

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.models.res.GoogleNewsJSON
import com.devpaul.infoxperu.domain.models.res.NewsItemJSON
import com.devpaul.infoxperu.domain.models.res.NewsSourceJSON
import com.devpaul.infoxperu.domain.ui.news_screen.errors.ErrorCard
import com.devpaul.infoxperu.domain.ui.news_screen.errors.NoNewsAvailableCard
import com.devpaul.infoxperu.domain.ui.skeleton.GoogleNewsSkeleton
import com.devpaul.infoxperu.feature.auth.Screen
import com.google.gson.Gson

@Composable
fun GoogleNewsCards(
    navController: NavController,
    selectedCountry: Country,
    googleNewsState: ResultState<GoogleNewsJSON>,
    context: Context
) {
    var showSkeleton by remember { mutableStateOf(true) }

    LaunchedEffect(googleNewsState) {
        showSkeleton = googleNewsState is ResultState.Loading
    }

    if (showSkeleton) {
        GoogleNewsSkeleton()
    } else {
        GoogleNewsContent(
            navController = navController,
            selectedCountry = selectedCountry,
            googleNewsState = googleNewsState,
            context = context
        )
    }
}

@Composable
fun GoogleNewsContent(
    navController: NavController,
    selectedCountry: Country,
    googleNewsState: ResultState<GoogleNewsJSON>,
    context: Context
) {
    when (googleNewsState) {
        is ResultState.Loading -> {
            GoogleNewsSkeleton()
        }

        is ResultState.Success -> {
            if (googleNewsState.data.newsItems.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedCountry.title + " " + googleNewsState.data.description,
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        val gson = Gson()
                        val countryJson = Uri.encode(gson.toJson(selectedCountry))
                        Text(
                            text = "Ver Más",
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Blue,
                            modifier = Modifier.clickable {
                                navController.navigate(
                                    Screen.AllNews.createRoute(
                                        "googleNews",
                                        countryJson
                                    )
                                )
                            }
                        )
                    }

                    LazyRow(
                        modifier = Modifier
                    ) {
                        items(googleNewsState.data.newsItems) { newsItem ->
                            GoogleNewsCard(newsItem, context)
                        }
                    }

                    Text(
                        text = "Cantidad: ${googleNewsState.data.newsItems.size}",
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 8.dp, end = 8.dp),
                    )
                }
            } else {
                NoNewsAvailableCard()
            }
        }

        is ResultState.Error -> {
            ErrorCard()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoogleNewsSectionPreview() {
    val googleNewsState = ResultState.Success(
        GoogleNewsJSON(
            title = "Titulo",
            link = "https://www.google.com",
            language = "es",
            lastBuildDate = "2021-09-01T00:00:00Z",
            description = "Google Noticias",
            newsItems = listOf(
                NewsItemJSON(
                    title = "Alerta roja desde mañana en 12 regiones del Perú",
                    link = "https://www.google.com",
                    guid = "1",
                    pubDate = "2021-09-01T00:00:00Z",
                    description = "Senamhi pronostica fenómeno de gran magnitud",
                    source = NewsSourceJSON("https://www.google.com", "Infobae Perú")
                ),
                NewsItemJSON(
                    title = "Perú inició su participación en el Campeonato Sudamericano de Atletismo Sub-20",
                    link = "https://www.google.com",
                    guid = "2",
                    pubDate = "2021-09-01T00:00:00Z",
                    description = "Primera jornada con buenas expectativas",
                    source = NewsSourceJSON("https://www.google.com", "Andina")
                )
            )
        )
    )

    GoogleNewsContent(
        navController = rememberNavController(),
        selectedCountry = Country("Perú", "pe", "general"),
        googleNewsState = googleNewsState,
        context = LocalContext.current
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewGoogleNewsCardsContentEmptyList() {
    NoNewsAvailableCard()
}

@Preview(showBackground = true)
@Composable
fun PreviewGoogleNewsError() {
    ErrorCard()
}

@Preview(showBackground = true)
@Composable
fun PreviewGoogleNewsLoading() {
    GoogleNewsContent(
        navController = rememberNavController(),
        selectedCountry = Country("Perú", "pe", "general"),
        googleNewsState = ResultState.Loading,
        context = LocalContext.current
    )
}

