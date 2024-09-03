package com.devpaul.infoxperu.domain.ui.news_screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.domain.models.res.GoogleNewsJSON
import com.devpaul.infoxperu.domain.models.res.NewsItemJSON
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.domain.models.res.NewsSourceJSON
import com.devpaul.infoxperu.domain.models.res.RedditResponse
import com.devpaul.infoxperu.domain.screen.atomic.NewsCountCard
import com.devpaul.infoxperu.domain.ui.news_screen.errors.ErrorCard
import com.devpaul.infoxperu.domain.ui.news_screen.errors.NoNewsAvailableCard
import com.devpaul.infoxperu.domain.ui.skeleton.AllNewsSkeleton
import com.devpaul.infoxperu.feature.home.news_view.NewsViewModel
import com.devpaul.infoxperu.ui.theme.Taupe
import com.devpaul.infoxperu.ui.theme.White
import com.google.gson.Gson
import timber.log.Timber

@Composable
fun AllNews(
    navController: NavHostController,
    newsType: String?,
    country: String?,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val gson = Gson()
    val decodedCountryJson = Uri.decode(country)
    val selectedCountry = gson.fromJson(decodedCountryJson, Country::class.java)

    val googleNewsState by viewModel.googleNewsState.collectAsState()
    val projectGDELTState by viewModel.projectGDELTState.collectAsState()
    val newsAPIState by viewModel.newsAPIState.collectAsState()
    val redditState by viewModel.redditState.collectAsState()

    val selectedState = when (newsType) {
        "googleNews" -> googleNewsState
        "projectGDELT" -> projectGDELTState
        "newsAPI" -> newsAPIState
        "reddit" -> redditState
        else -> ResultState.Error(Exception("Invalid news type"))
    }

    val context = LocalContext.current
    var showSkeleton by remember { mutableStateOf(true) }

    LaunchedEffect(newsType) {
        when (newsType) {
            "googleNews" -> {
                viewModel.getGoogleNews(
                    limit = 0,
                    query = selectedCountry.category,
                    language = "es"
                )
            }

            "projectGDELT" -> {
                viewModel.getProjectGDELTNews(
                    limit = 0,
                    query = selectedCountry.category,
                    mode = "ArtList",
                    format = "json"
                )
            }

            "reddit" -> {
                viewModel.getRedditNews(limit = 0, country = selectedCountry.category)
            }

            "newsAPI" -> {
                viewModel.getNewsAPI(limit = 0, initLetters = selectedCountry.initLetters)
            }

            else -> {
                Timber.e("Invalid news type: $newsType")
            }
        }
    }
    LaunchedEffect(selectedState) {
        showSkeleton = selectedState is ResultState.Loading
    }

    if (showSkeleton) {
        AllNewsSkeleton()
    } else {
        AllNewsContent(
            navController = navController,
            selectedCountry = selectedCountry,
            newsState = selectedState,
            context = context
        )
    }
}

@Composable
fun AllNewsContent(
    navController: NavController,
    selectedCountry: Country,
    newsState: ResultState<*>,
    context: Context
) {
    when (newsState) {
        is ResultState.Success -> {
            when (val data = newsState.data) {
                is GoogleNewsJSON -> VerticalCardList(data = data, context = context)
                is GDELProject -> VerticalCardList(data = data, context = context)
                is RedditResponse -> VerticalCardList(data = data, context = context)
                is NewsResponse -> VerticalCardList(data = data, context = context)
                else -> NoNewsAvailableCard()
            }
        }

        is ResultState.Error -> ErrorCard()
        else -> NoNewsAvailableCard()
    }
}

@Composable
fun VerticalCardList(data: Any, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Taupe)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 24.dp, start = 8.dp, end = 8.dp, bottom = 60.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when (data) {
                is GoogleNewsJSON -> {
                    NewsCountCard(countText = "Cantidad de noticias: ${data.newsItems.size}")

                    data.newsItems.forEach { item ->
                        NewsCard(
                            context = context,
                            title = item.title,
                            summary = item.description.toString(),
                            url = item.link
                        )
                    }
                }

                is GDELProject -> {
                    NewsCountCard(
                        countText = "Cantidad de noticias: ${data.articles.size}",
                        containerColor = White
                    )
                    data.articles.forEach { item ->
                        NewsCard(
                            context = context,
                            title = item.title,
                            summary = item.domain,
                            url = item.url
                        )
                    }
                }

                is RedditResponse -> {
                    NewsCountCard(
                        countText = "Cantidad de posts: ${data.data.children.size}",
                        containerColor = White
                    )
                    data.data.children.forEach { post ->
                        NewsCard(
                            context = context,
                            title = post.data.title.toString(),
                            summary = post.data.author,
                            url = post.data.url.toString()
                        )
                    }
                }

                is NewsResponse -> {
                    NewsCountCard(
                        countText = "Cantidad de noticias: ${data.articles.size}",
                        containerColor = White
                    )
                    data.articles.forEach { article ->
                        NewsCard(
                            context = context,
                            title = article.title,
                            summary = article.author.toString(),
                            url = article.url
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun NewsCard(context: Context, title: String, summary: String, url: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        onClick = {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    NewsCard(
        context = LocalContext.current,
        title = "Sample Title",
        summary = "Sample summary for the news item.",
        url = "https://www.google.com"
    )
}

@Preview(showBackground = true)
@Composable
fun VerticalCardListPreview() {
    VerticalCardList(
        context = LocalContext.current,
        data =
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
}
