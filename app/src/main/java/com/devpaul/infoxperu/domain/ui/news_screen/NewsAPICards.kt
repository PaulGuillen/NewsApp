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
import com.devpaul.infoxperu.domain.models.res.ArticleNewsResponse
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.domain.models.res.SourceResponse
import com.devpaul.infoxperu.domain.ui.news_screen.errors.ErrorCard
import com.devpaul.infoxperu.domain.ui.news_screen.errors.NoNewsAvailableCard
import com.devpaul.infoxperu.domain.ui.skeleton.NewsAPISkeleton
import com.devpaul.infoxperu.feature.user_start.Screen
import com.google.gson.Gson

@Composable
fun NewsAPICards(
    navController: NavController,
    selectedCountry: Country,
    newsAPIState: ResultState<NewsResponse>,
    context: Context
) {
    var showSkeleton by remember { mutableStateOf(true) }

    LaunchedEffect(newsAPIState) {
        showSkeleton = newsAPIState is ResultState.Loading
    }

    if (showSkeleton) {
        NewsAPISkeleton()
    } else {
        NewsAPICardsContent(
            navController = navController,
            selectedCountry = selectedCountry,
            newsAPIState = newsAPIState,
            context = context
        )
    }
}

@Composable
fun NewsAPICardsContent(
    navController: NavController,
    selectedCountry: Country,
    newsAPIState: ResultState<NewsResponse>,
    context: Context
) {
    when (newsAPIState) {
        is ResultState.Loading -> {
            NewsAPISkeleton()
        }

        is ResultState.Success -> {
            if (newsAPIState.data.articles.isNotEmpty()) {
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
                            text = "NewsAPI Noticias",
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
                                        "newsAPI",
                                        countryJson
                                    )
                                )
                            }
                        )
                    }

                    LazyRow(
                        modifier = Modifier
                    ) {
                        items(newsAPIState.data.articles) { newsItem ->
                            NewsSingleCard(newsItem, context)
                        }
                    }

                    Text(
                        text = "Cantidad: ${newsAPIState.data.articles.size}",
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
fun NewsAPICardsPreview() {
    val newsAPIState = ResultState.Success(
        NewsResponse(
            status = "ok",
            totalResults = 1,
            articles = listOf(
                ArticleNewsResponse(
                    source = SourceResponse(
                        id = "1",
                        name = "Fuente 1"
                    ),
                    author = "Autor 1",
                    title = "Título 1",
                    articleDescription = "Descripción 1",
                    url = "https://www.google.com",
                    imageUrl = "https://www.google.com",
                    publishDate = "2021-09-01T00:00:00Z",
                    content = "Contenido 1"
                ),
                ArticleNewsResponse(
                    source = SourceResponse(
                        id = "1",
                        name = "Fuente 1"
                    ),
                    author = "Autor 1",
                    title = "Título 1",
                    articleDescription = "Descripción 1",
                    url = "https://www.google.com",
                    imageUrl = "https://www.google.com",
                    publishDate = "2021-09-01T00:00:00Z",
                    content = "Contenido 1"
                )
            )
        )
    )

    NewsAPICardsContent(
        navController = rememberNavController(),
        selectedCountry = Country("Perú", "pe", "general"),
        newsAPIState = newsAPIState,
        context = LocalContext.current
    )
}

@Preview(showBackground = true)
@Composable
fun NewsAPICardsContentEmptyList() {
    NoNewsAvailableCard()
}

@Preview(showBackground = true)
@Composable
fun NewsAPICardsNewsError() {
    ErrorCard()
}

@Preview(showBackground = true)
@Composable
fun NewsAPICardsLoading() {
    NewsAPICardsContent(
        navController = rememberNavController(),
        selectedCountry = Country("Perú", "pe", "general"),
        newsAPIState = ResultState.Loading,
        context = LocalContext.current
    )
}

