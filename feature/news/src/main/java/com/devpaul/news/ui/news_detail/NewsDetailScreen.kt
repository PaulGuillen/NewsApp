package com.devpaul.news.ui.news_detail

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.news.data.datasource.mock.NewsMock
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity
import com.devpaul.news.ui.news_detail.components.NewsCard
import com.devpaul.news.ui.news_detail.components.NewsCountCard
import com.devpaul.shared.ui.components.atoms.OutlinedLoadMoreButton
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
                canLoadMore = canLoadMore,
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
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    NewsCountCard("Cantidad de noticias: ${googleState.response.data.totalItems}")
                }
                items(googleState.response.data.items) { item ->
                    NewsCard(
                        context = context,
                        title = item.title,
                        summary = item.description,
                        url = item.link,
                        date = item.pubDate,
                        author = item.source.name,
                    )
                }
                if (canLoadMore) {
                    item {
                        OutlinedLoadMoreButton(onClick = onLoadMore)
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
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    NewsCountCard("Cantidad de noticias: ${deltaProjectState.response.data.totalItems}")
                }
                items(deltaProjectState.response.data.items) { item ->
                    NewsCard(context, item.title, item.domain, item.url, "", "")
                }
                if (canLoadMore) {
                    item {
                        OutlinedLoadMoreButton(onClick = onLoadMore)
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
                    .padding(horizontal = 8.dp)
            ) {
                item {
                    NewsCountCard("Cantidad de posts: ${redditState.response.data.totalItems}")
                }
                items(redditState.response.data.items) { post ->
                    NewsCard(
                        context = context,
                        title = post.title,
                        summary = post.selfText,
                        url = post.url,
                        date = post.createdAt,
                        author = post.authorFullname,
                    )
                }
                if (canLoadMore) {
                    item {
                        OutlinedLoadMoreButton(onClick = onLoadMore)
                    }
                }
            }
        }

        is ResultState.Error -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCountCardPreview() {
    NewsCountCard(title = "Cantidad de noticias: 10")
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    NewsCard(
        context = LocalContext.current,
        title = "Título de prueba",
        summary = "Resumen de prueba para la tarjeta de noticias.",
        url = "https://example.com",
        date = "01/01/2023",
        author = "Autor de prueba"
    )
}

@Preview(showBackground = true)
@Composable
fun NewsDetailsScreenPreview() {
    GoogleNewsList(
        googleState = ResultState.Success(NewsMock().googleMock),
        context = LocalContext.current,
        modifier = Modifier.fillMaxWidth(),
        onLoadMore = {},
        canLoadMore = true
    )
}