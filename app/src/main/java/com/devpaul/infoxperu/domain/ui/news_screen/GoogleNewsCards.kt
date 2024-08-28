package com.devpaul.infoxperu.domain.ui.news_screen

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.GoogleNewsJSON
import com.devpaul.infoxperu.domain.models.res.NewsItemJSON
import com.devpaul.infoxperu.domain.models.res.NewsSourceJSON
import com.devpaul.infoxperu.domain.ui.skeleton.GoogleNewsSkeleton
import com.devpaul.infoxperu.ui.theme.Black
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun GoogleNewsCards(googleNewsState: ResultState<GoogleNewsJSON>, context: Context) {
    var showSkeleton by remember { mutableStateOf(true) }

    LaunchedEffect(googleNewsState) {
        showSkeleton = googleNewsState is ResultState.Loading
    }

    if (showSkeleton) {
        GoogleNewsSkeleton()
    } else {
        GoogleNewsContent(googleNewsState = googleNewsState, context = context)
    }
}

@Composable
fun GoogleNewsContent(googleNewsState: ResultState<GoogleNewsJSON>, context: Context) {
    when (googleNewsState) {
        is ResultState.Loading -> {
            GoogleNewsSkeleton()
        }

        is ResultState.Success -> {
            if (googleNewsState.data.newsItems.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 10.dp)
                ) {
                    googleNewsState.data.newsItems.forEach { newsItem ->
                        GoogleNewsCard(newsItem, context)
                    }
                }
            } else {
                Text(text = "No hay secciones disponibles.")
            }
        }

        is ResultState.Error -> {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp)
            ) {
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
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
                            text = "Ha ocurrido un error.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Black,
                        )
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGoogleNewsCardsContent() {
    val googleNewsState = ResultState.Success(
        GoogleNewsJSON(
            title = "Titulo",
            link = "https://www.google.com",
            language = "es",
            lastBuildDate = "2021-09-01T00:00:00Z",
            description = "Descripcion",
            newsItems = listOf(
                NewsItemJSON(
                    title = "Titulo",
                    link = "https://www.google.com",
                    guid = "1",
                    pubDate = "2021-09-01T00:00:00Z",
                    description = "Descripcion",
                    source = NewsSourceJSON("https://www.google.com")
                )

            )
        )
    )
    GoogleNewsContent(googleNewsState = googleNewsState, context = LocalContext.current)
}

@Preview(showBackground = true)
@Composable
fun PreviewGoogleNewsError() {
    GoogleNewsContent(
        googleNewsState = ResultState.Error(Exception("Simulated Error")),
        context = LocalContext.current
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewGoogleNewsLoading() {
    GoogleNewsContent(googleNewsState = ResultState.Loading, context = LocalContext.current)
}

