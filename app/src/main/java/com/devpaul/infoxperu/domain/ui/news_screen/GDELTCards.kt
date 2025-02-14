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
import com.devpaul.infoxperu.domain.models.res.Article
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.domain.ui.news_screen.errors.ErrorCard
import com.devpaul.infoxperu.domain.ui.news_screen.errors.NoNewsAvailableCard
import com.devpaul.infoxperu.domain.ui.skeleton.RedditSkeleton
import com.devpaul.infoxperu.feature.auth.Screen
import com.google.gson.Gson

@Composable
fun GDELTCards(
    navController: NavController,
    selectedCountry: Country,
    projectGDELTState: ResultState<GDELProject>,
    context: Context
) {
    var showSkeleton by remember { mutableStateOf(true) }

    LaunchedEffect(projectGDELTState) {
        showSkeleton = projectGDELTState is ResultState.Loading
    }

    if (showSkeleton) {
        RedditSkeleton()
    } else {
        GDELTCardsContent(
            navController = navController,
            selectedCountry = selectedCountry,
            projectGDELTState = projectGDELTState,
            context = context
        )
    }

}

@Composable
fun GDELTCardsContent(
    navController: NavController,
    selectedCountry: Country,
    projectGDELTState: ResultState<GDELProject>?,
    context: Context
) {

    when (projectGDELTState) {
        is ResultState.Loading -> {
            RedditSkeleton()
        }

        is ResultState.Success -> {
            if (projectGDELTState.data.articles.isNotEmpty()) {
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
                                        "projectGDELT",
                                        countryJson
                                    )
                                )
                            }
                        )
                    }

                    LazyRow(
                        modifier = Modifier
                    ) {
                        items(projectGDELTState.data.articles) { articleItem ->
                            GDELTCard(article = articleItem, context = context)
                        }
                    }

                    Text(
                        text = "Cantidad: ${projectGDELTState.data.articles.size}",
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

        else -> {
            ErrorCard()
        }

    }
}

@Preview(showBackground = true, name = "GDELTCards Content - Success")
@Composable
fun PreviewGDELTCardsSuccess() {
    val sampleData = GDELProject(
        listOf(
            Article(
                "https://www.deperu.com/tv/wQfz0Keo-Os.venezuela-vs-canada-penales-resumen-y-goles-copa-america-2024-libero.UCk2OZrA0E6q6xp4bBKtf9KA.html",
                "",
                "Video : ? VENEZUELA VS CANADÁ PENALES , RESUMEN Y GOLES - COPA AMÉRICA 2024",
                "20240707T020000Z",
                "https://i.ytimg.com/vi/wQfz0Keo-Os/hqdefault.jpg",
                "deperu.com",
                "Galician",
                "United States",
            ),
            Article(
                "https://www.deperu.com/tv/wQfz0Keo-Os.venezuela-vs-canada-penales-resumen-y-goles-copa-america-2024-libero.UCk2OZrA0E6q6xp4bBKtf9KA.html",
                "",
                "Video : ? VENEZUELA VS CANADÁ PENALES , RESUMEN Y GOLES - COPA AMÉRICA 2024",
                "20240707T020000Z",
                "https://i.ytimg.com/vi/wQfz0Keo-Os/hqdefault.jpg",
                "deperu.com",
                "Galician",
                "United",
            ),
        )
    )
    GDELTCardsContent(
        navController = rememberNavController(),
        selectedCountry = Country("Perú", "pe", "general"),
        projectGDELTState = ResultState.Success(sampleData),
        context = LocalContext.current
    )
}

@Preview(showBackground = true, name = "GDELTCards Content - Error")
@Composable
fun PreviewGDELTCardsError() {
    GDELTCardsContent(
        navController = rememberNavController(),
        selectedCountry = Country("Perú", "pe", "general"),
        projectGDELTState = ResultState.Error(Exception("Error de prueba")),
        context = LocalContext.current
    )
}

@Preview(showBackground = true, name = "GDELTCards Content - Loading")
@Composable
fun PreviewGDELTCardsLoading() {
    GDELTCardsContent(
        navController = rememberNavController(),
        selectedCountry = Country("Perú", "pe", "general"),
        projectGDELTState = ResultState.Loading,
        context = LocalContext.current
    )
}

