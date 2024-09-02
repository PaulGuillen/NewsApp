package com.devpaul.infoxperu.domain.ui.news_screen

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.domain.models.res.GoogleNewsJSON
import com.devpaul.infoxperu.domain.models.res.NewsItemJSON
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.domain.models.res.NewsSourceJSON
import com.devpaul.infoxperu.domain.models.res.RedditResponse
import com.devpaul.infoxperu.domain.ui.news_screen.errors.ErrorCard
import com.devpaul.infoxperu.domain.ui.news_screen.errors.NoNewsAvailableCard
import com.devpaul.infoxperu.domain.ui.skeleton.AllNewsSkeleton
import com.devpaul.infoxperu.feature.home.news_view.NewsViewModel
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
                viewModel.getGoogleNews(query = selectedCountry.category, language = "es")
            }
            "projectGDELT" -> {
                viewModel.getProjectGDELTNews(
                    query = selectedCountry.category,
                    mode = "ArtList",
                    format = "json"
                )
            }
            "reddit" -> {
                viewModel.getRedditNews(country = selectedCountry.category)
            }
            "newsAPI" -> {
                viewModel.getNewsAPI(initLetters = selectedCountry.initLetters)
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
                is GoogleNewsJSON -> VerticalCardList(data = data)
                is GDELProject -> VerticalCardList(data = data)
                is RedditResponse -> VerticalCardList(data = data)
                is NewsResponse -> VerticalCardList(data = data)
                else -> NoNewsAvailableCard()
            }
        }

        is ResultState.Error -> ErrorCard()
        else -> NoNewsAvailableCard()
    }
}

@Composable
fun VerticalCardList(data: Any) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 60.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        when (data) {
            is GoogleNewsJSON -> {
                data.newsItems.forEach { item ->
                    NewsCard(title = item.title, summary = item.description.toString())
                }
            }

            is GDELProject -> {
                data.articles.forEach { item ->
                    NewsCard(title = item.title, summary = item.domain)
                }
            }

            is RedditResponse -> {
                data.data.children.forEach { post ->
                    NewsCard(title = post.data.title.toString(), summary = post.data.author)
                }
            }

            is NewsResponse -> {
                data.articles.forEach { article ->
                    NewsCard(title = article.title, summary = article.author.toString())
                }
            }
        }
    }
}

@Composable
fun NewsCard(title: String, summary: String) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            Text(text = summary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    NewsCard(title = "Sample Title", summary = "Sample summary for the news item.")
}

@Preview(showBackground = true)
@Composable
fun VerticalCardListPreview() {
    VerticalCardList(
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
