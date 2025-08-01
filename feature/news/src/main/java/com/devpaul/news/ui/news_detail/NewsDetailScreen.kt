package com.devpaul.news.ui.news_detail

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.news.data.datasource.mock.NewsMock
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity
import com.devpaul.news.ui.news_detail.components.NewsCard
import com.devpaul.news.ui.news_detail.components.NewsCountCard
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType
import com.devpaul.shared.ui.components.atoms.base.button.OutlinedLoadMoreButton
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
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
    val selectedUrl by viewModel.selectedUrl
    val canLoadMore = viewModel.canLoadMore(newsType ?: "")

    BaseScreenWithState(
        viewModel = viewModel,
        onInit = { _, _ -> viewModel.setNewsData(newsType, country) },
        navController = navController,
        onBackPressed = { onBackWithResult -> onBackWithResult("shouldReload", true) }
    ) { innerPadding, uiState, onIntent, _, _ ->
        NewsDetailContent(
            context = context,
            innerPadding = innerPadding,
            uiState = uiState,
            newsType = newsType,
            selectedUrl = selectedUrl,
            canLoadMore = canLoadMore,
            onSelectUrl = { viewModel.selectUrl(it) },
            onLoadMore = { onIntent(NewsDetailUiIntent.LoadNextPage(newsType ?: "")) }
        )
    }
}

@Composable
fun NewsDetailContent(
    context: Context,
    innerPadding: PaddingValues,
    uiState: NewsDetailUiState,
    newsType: String?,
    selectedUrl: String?,
    canLoadMore: Boolean,
    onSelectUrl: (String) -> Unit,
    onLoadMore: () -> Unit
) {
    BaseContentLayout(
        isBodyScrollable = false,
        applyStatusBarsPaddingToHeader = false,
        applyNavigationBarsPaddingToFooter = false,
        header = {
            NewsDetailHeader(newsType = newsType, uiState = uiState)
        },
        body = {
            NewsDetailBody(
                context = context,
                innerPadding = innerPadding,
                uiState = uiState,
                newsType = newsType,
                selectedUrl = selectedUrl,
                onSelectUrl = onSelectUrl
            )
        },
        footer = {
            NewsDetailFooter(
                canLoadMore = canLoadMore,
                isLoadingMore = uiState.isLoadingMore,
                onLoadMore = onLoadMore
            )
        }
    )
}

@Composable
fun NewsDetailHeader(
    newsType: String?,
    uiState: NewsDetailUiState
) {
    val totalItems = when (newsType) {
        "googleNews" -> (uiState.google as? ResultState.Success)?.response?.data?.totalItems ?: 0
        "redditNews" -> (uiState.reddit as? ResultState.Success)?.response?.data?.totalItems ?: 0
        "deltaProjectNews" -> (uiState.deltaProject as? ResultState.Success)?.response?.data?.totalItems
            ?: 0

        else -> 0
    }

    if (totalItems > 0) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 10.dp)
                .fillMaxWidth()
        ) {
            NewsCountCard(
                title = when (newsType) {
                    "redditNews" -> "Cantidad de posts: $totalItems"
                    else -> "Cantidad de noticias: $totalItems"
                }
            )
        }

    }
}

@Composable
fun NewsDetailBody(
    context: Context,
    innerPadding: PaddingValues,
    uiState: NewsDetailUiState,
    newsType: String?,
    selectedUrl: String?,
    onSelectUrl: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 64.dp)
            .fillMaxWidth()
    ) {
        when (newsType) {
            "googleNews" -> {
                GoogleNewsList(
                    googleState = uiState.google,
                    context = context,
                    modifier = Modifier.padding(innerPadding),
                    selectedUrl = selectedUrl,
                    onSelectUrl = onSelectUrl
                )
            }

            "redditNews" -> {
                RedditNewsList(
                    redditState = uiState.reddit,
                    context = context,
                    modifier = Modifier.padding(innerPadding),
                    selectedUrl = selectedUrl,
                    onSelectUrl = onSelectUrl
                )
            }

            "deltaProjectNews" -> {
                DeltaNewsList(
                    deltaProjectState = uiState.deltaProject,
                    context = context,
                    modifier = Modifier.padding(innerPadding),
                    selectedUrl = selectedUrl,
                    onSelectUrl = onSelectUrl
                )
            }
        }
    }
}

@Composable
fun NewsDetailFooter(
    canLoadMore: Boolean,
    isLoadingMore: Boolean,
    onLoadMore: () -> Unit
) {
    if (canLoadMore) {
        if (isLoadingMore) {
            Column(
                modifier = Modifier.padding(4.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                )
            }
        } else {
            Column(
                modifier = Modifier.padding(horizontal = 6.dp)
            ) {
                OutlinedLoadMoreButton(
                    onClick = onLoadMore
                )
            }

        }
    }
}

@Composable
fun GoogleNewsList(
    googleState: ResultState<GoogleEntity>,
    context: Context,
    modifier: Modifier,
    selectedUrl: String?,
    onSelectUrl: (String) -> Unit
) {
    when (googleState) {
        is ResultState.Loading -> SkeletonRenderer(type = SkeletonType.NEWS_DETAIL)
        is ResultState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(modifier)
                    .padding(horizontal = 8.dp)
            ) {
                items(googleState.response.data.items) { item ->
                    NewsCard(
                        context = context,
                        title = item.title,
                        summary = item.description,
                        url = item.link,
                        date = item.pubDate,
                        author = item.source.name,
                        isSelected = selectedUrl == item.link,
                        onSelect = { onSelectUrl(item.link) }
                    )
                }
            }
        }

        is ResultState.Error -> {}
        else -> {}
    }
}

@Composable
fun DeltaNewsList(
    deltaProjectState: ResultState<DeltaProjectEntity>,
    context: Context,
    modifier: Modifier,
    selectedUrl: String?,
    onSelectUrl: (String) -> Unit
) {
    when (deltaProjectState) {
        is ResultState.Loading -> SkeletonRenderer(type = SkeletonType.NEWS_DETAIL)
        is ResultState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(modifier)
                    .padding(horizontal = 8.dp)
            ) {
                items(deltaProjectState.response.data.items) { item ->
                    NewsCard(
                        context = context,
                        title = item.title,
                        summary = item.domain,
                        url = item.url,
                        date = item.seenDate,
                        author = item.sourceCountry,
                        isSelected = selectedUrl == item.url,
                        onSelect = { onSelectUrl(item.url) }
                    )
                }
            }
        }

        is ResultState.Error -> {}
        else -> {}
    }
}

@Composable
fun RedditNewsList(
    redditState: ResultState<RedditEntity>,
    context: Context,
    modifier: Modifier,
    selectedUrl: String?,
    onSelectUrl: (String) -> Unit
) {
    when (redditState) {
        is ResultState.Loading -> SkeletonRenderer(type = SkeletonType.NEWS_DETAIL)
        is ResultState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(modifier)
                    .padding(horizontal = 8.dp)
            ) {
                items(redditState.response.data.items) { post ->
                    NewsCard(
                        context = context,
                        title = post.title,
                        summary = post.selfText,
                        url = post.url,
                        date = post.createdAt,
                        author = post.authorFullname,
                        isSelected = selectedUrl == post.url,
                        onSelect = { onSelectUrl(post.url) }
                    )
                }
            }
        }

        is ResultState.Error -> {}
        else -> {}
    }
}

@Preview(
    showBackground = true,
    name = "NewsDetailContentPreview - Landscape",
    uiMode = android.content.res.Configuration.UI_MODE_TYPE_NORMAL,

    )
@Composable
fun NewsDetailContentPreview() {
    val context = LocalContext.current

    NewsDetailContent(
        context = context,
        innerPadding = PaddingValues(0.dp),
        uiState = NewsDetailUiState(
            google = ResultState.Success(NewsMock().googleMock),
            reddit = ResultState.Loading,
            deltaProject = ResultState.Loading,
            isLoadingMore = false
        ),
        newsType = "googleNews",
        selectedUrl = null,
        canLoadMore = true,
        onSelectUrl = {},
        onLoadMore = {}
    )
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
        author = "Autor de prueba",
        isSelected = false,
        onSelect = {}
    )
}

@Preview(showBackground = true)
@Composable
fun NewsDetailsScreenPreview() {
    GoogleNewsList(
        googleState = ResultState.Success(NewsMock().googleMock),
        context = LocalContext.current,
        modifier = Modifier.fillMaxWidth(),
        selectedUrl = null,
        onSelectUrl = {}
    )
}