package com.devpaul.news.ui.news_detail

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.Taupe
import com.devpaul.core_platform.theme.White
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity
import com.devpaul.shared.ui.components.atoms.skeleton.NewsDetailSkeleton
import com.devpaul.shared.ui.components.molecules.TopBar
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsDetailsScreen(
    navController: NavHostController,
    newsType: String?,
    country: String?
) {
    val viewModel: NewsDetailViewModel = koinViewModel()
    val context = LocalContext.current

    LaunchedEffect(newsType, country) {
        viewModel.setNewsData(newsType, country)
    }

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
        onBackPressed = { onBackWithResult -> onBackWithResult("shouldReload", true) }
    ) { _, uiState, _, _, _ ->
        Scaffold(
            topBar = {
                TopBar(title = stringResource(R.string.app_name))
            }
        ) { innerPadding ->
            NewsDetailContent(
                context = context,
                innerPadding = innerPadding,
                uiState = uiState,
                newsType = newsType,
                onLoadMore = {
                    viewModel.loadNextPage(newsType ?: "")
                }
            )
        }
    }
}

@Composable
fun NewsDetailContent(
    context: Context,
    innerPadding: PaddingValues,
    uiState: NewsDetailUiState,
    newsType: String?,
    onLoadMore: () -> Unit
) {
    val viewModel: NewsDetailViewModel = koinViewModel()
    val canLoadMore = viewModel.canLoadMore(newsType ?: "")

    when (newsType) {
        "googleNews" -> {
            GoogleNewsList(
                googleState = uiState.google,
                context = context,
                modifier = Modifier.padding(innerPadding),
                onLoadMore = onLoadMore,
                canLoadMore = canLoadMore,
            )
        }

        "redditNews" -> {
            RedditNewsList(
                redditState = uiState.reddit,
                context = context,
                modifier = Modifier.padding(innerPadding),
                onLoadMore = onLoadMore,
                canLoadMore  = canLoadMore,
            )
        }

        "deltaProjectNews" -> {
            DeltaNewsList(
                deltaProjectState = uiState.deltaProject,
                context = context,
                modifier = Modifier.padding(innerPadding),
                onLoadMore = onLoadMore,
                canLoadMore = canLoadMore,
            )
        }
    }
}

@Composable
fun GoogleNewsList(
    googleState: ResultState<GoogleEntity>,
    context: Context,
    modifier: Modifier,
    onLoadMore: () -> Unit,
    canLoadMore: Boolean
) {
    when (googleState) {
        is ResultState.Loading -> NewsDetailSkeleton(modifier)
        is ResultState.Success -> {
            LazyColumn(
                modifier = modifier
                    .background(Taupe)
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    NewsCountCard("Cantidad de noticias: ${googleState.response.data.totalItems}")
                }
                items(googleState.response.data.items) { item ->
                    NewsCard(context, item.title, item.description, item.link)
                }
                if (canLoadMore) {
                    item {
                        Button(
                            onClick = onLoadMore,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Cargar más")
                        }
                    }
                }
            }
        }

        is ResultState.Error -> {}
    }
}

@Composable
fun DeltaNewsList(
    deltaProjectState: ResultState<DeltaProjectEntity>,
    context: Context,
    modifier: Modifier,
    onLoadMore: () -> Unit,
    canLoadMore: Boolean
) {
    when (deltaProjectState) {
        is ResultState.Loading -> NewsDetailSkeleton(modifier)
        is ResultState.Success -> {
            LazyColumn(
                modifier = modifier
                    .background(Taupe)
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    NewsCountCard("Cantidad de noticias: ${deltaProjectState.response.data.totalItems}")
                }
                items(deltaProjectState.response.data.items) { item ->
                    NewsCard(context, item.title, item.domain, item.url)
                }
                if (canLoadMore) {
                    item {
                        Button(
                            onClick = onLoadMore,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Cargar más")
                        }
                    }
                }
            }
        }

        is ResultState.Error -> {}
    }
}

@Composable
fun RedditNewsList(
    redditState: ResultState<RedditEntity>,
    context: Context,
    modifier: Modifier,
    onLoadMore: () -> Unit,
    canLoadMore: Boolean
) {
    when (redditState) {
        is ResultState.Loading -> NewsDetailSkeleton(modifier)
        is ResultState.Success -> {
            LazyColumn(
                modifier = modifier
                    .background(Taupe)
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    NewsCountCard("Cantidad de posts: ${redditState.response.data.totalItems}")
                }
                items(redditState.response.data.items) { post ->
                    NewsCard(context, post.title, post.author, post.url)
                }
                if (canLoadMore) {
                    item {
                        Button(
                            onClick = onLoadMore,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Cargar más")
                        }
                    }
                }
            }
        }

        is ResultState.Error -> {}
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