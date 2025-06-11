package com.devpaul.news.ui.news_detail

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.Taupe
import com.devpaul.core_platform.theme.White
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity
import com.devpaul.shared.screen.BaseScreenWithState
import com.devpaul.shared.ui.extension.TopBar
import com.devpaul.shared.ui.skeleton.NewsDetailSkeleton
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsDetailsScreen(
    navController: NavHostController,
    newsType: String?,
    country: String?
) {
    val viewModel: NewsDetailViewModel = koinViewModel()
    val context = LocalContext.current
    val uiModel = remember { mutableStateOf(NewsDetailUiModel()) }

    LaunchedEffect(newsType, country) {
        viewModel.setNewsData(newsType, country)
    }

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
        onUiEvent = { event, _ ->
            when (event) {
                is NewsDetailUiEvent.GoogleSuccess ->
                    uiModel.value = uiModel.value.copy(google = event.response)

                is NewsDetailUiEvent.GoogleError ->
                    uiModel.value =
                        uiModel.value.copy(googleError = event.error.apiErrorResponse?.message)

                is NewsDetailUiEvent.DeltaProjectSuccess ->
                    uiModel.value = uiModel.value.copy(deltaProject = event.response)

                is NewsDetailUiEvent.DeltaProjectError ->
                    uiModel.value =
                        uiModel.value.copy(deltaProjectError = event.error.apiErrorResponse?.message)

                is NewsDetailUiEvent.RedditSuccess ->
                    uiModel.value = uiModel.value.copy(reddit = event.response)

                is NewsDetailUiEvent.RedditError ->
                    uiModel.value =
                        uiModel.value.copy(redditError = event.error.apiErrorResponse?.message)
            }
        },
        onBackPressed = { onBackWithResult -> onBackWithResult("shouldReload", true) }
    ) { _, uiState, _, _, _ ->
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.app_name),
                )
            }
        ) { innerPadding ->
            NewsDetailContent(
                context = context,
                innerPadding = innerPadding,
                uiState = uiState,
                uiModel = uiModel.value,
                newsType = newsType
            )
        }
    }
}

@Composable
fun NewsDetailContent(
    context: Context,
    innerPadding: PaddingValues,
    uiState: NewsDetailUiState,
    uiModel: NewsDetailUiModel,
    newsType: String?
) {
    if (uiState.isNewsDetailLoading) {
        NewsDetailSkeleton(modifier = Modifier.padding(innerPadding))
        return
    }

    when (newsType) {
        "googleNews" -> {
            uiModel.google?.let {
                GoogleNewsList(it, context, Modifier.padding(innerPadding))
            }
        }

        "redditNews" -> {
            uiModel.reddit?.let {
                RedditNewsList(it, context, Modifier.padding(innerPadding))
            }
        }

        "deltaProjectNews" -> {
            uiModel.deltaProject?.let {
                DeltaNewsList(it, context, Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun GoogleNewsList(response: GoogleEntity, context: Context, modifier: Modifier) {
    NewsListContainer(modifier) {
        NewsCountCard("Cantidad de noticias: ${response.data.totalItems}")
        response.data.items.forEach { item ->
            NewsCard(context, item.title, item.description, item.link)
        }
    }
}

@Composable
fun DeltaNewsList(response: DeltaProjectEntity, context: Context, modifier: Modifier) {
    NewsListContainer(modifier) {
        NewsCountCard("Cantidad de noticias: ${response.data.totalItems}")
        response.data.items.forEach { item ->
            NewsCard(context, item.title, item.domain, item.url)
        }
    }
}

@Composable
fun RedditNewsList(response: RedditEntity, context: Context, modifier: Modifier) {
    NewsListContainer(modifier) {
        NewsCountCard("Cantidad de posts: ${response.data.totalItems}")
        response.data.items.forEach { post ->
            NewsCard(context, post.title, post.author, post.url)
        }
    }
}

@Composable
private fun NewsListContainer(modifier: Modifier, content: @Composable () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Taupe)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            content()
        }
    }
}

@Composable
fun NewsCountCard(title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun NewsCard(context: Context, title: String, summary: String, url: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        onClick = {
            context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        }
    }
}