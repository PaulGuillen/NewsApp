package com.devpaul.news.ui.news

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.extension.toDateText
import com.devpaul.core_platform.extension.toReadableDate
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.news.data.datasource.mock.NewsMock
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.ui.news.components.country.CountryCards
import com.devpaul.news.ui.news.components.news.ErrorRetryCard
import com.devpaul.shared.ui.components.organisms.LoadMoreFooter
import com.devpaul.news.ui.news.components.news.NewsCard
import com.devpaul.shared.ui.components.organisms.sourceselector.Source
import com.devpaul.shared.ui.components.organisms.sourceselector.NewsSourceSelector
import com.devpaul.news.ui.news.components.news.rememberUiPagination
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType
import com.devpaul.shared.ui.components.molecules.BottomNavigationBar
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(navController: NavHostController) {

    val viewModel: NewsViewModel = koinViewModel()
    val context = LocalContext.current
    val selectedUrl by viewModel.selectedUrl

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
        observeBackKeys = listOf("shouldReload"),
        onBackResults = { results, uiState, onIntent ->
            if (results["shouldReload"] == true && uiState.selectedCountry != null) {
                onIntent(NewsUiIntent.GetCountries)
                onIntent(NewsUiIntent.SelectCountry(uiState.selectedCountry))
            }
        },
    ) { _, uiState, onIntent, _, _ ->
        BaseContentLayout(
            isBodyScrollable = false,
            body = {
                NewsBody(
                    context = context,
                    uiState = uiState,
                    onIntent = onIntent,
                    onCountrySelected = { countryItem ->
                        onIntent(NewsUiIntent.SelectCountry(countryItem))
                    },
                    selectedUrl = selectedUrl,
                    onSelectUrl = { viewModel.selectUrl(it) },
                )
            },
            footer = {
                BottomNavigationBar(navController)
            }
        )
    }
}

@Composable
fun NewsBody(
    context: Context,
    uiState: NewsUiState,
    onIntent: (NewsUiIntent) -> Unit,
    onCountrySelected: (CountryItemEntity) -> Unit,
    selectedUrl: String?,
    onSelectUrl: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CountryCards(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .fillMaxWidth(),
            countryState = uiState.country,
            selectedCountry = uiState.selectedCountry,
            onCountrySelected = onCountrySelected
        )

        if (uiState.selectedCountry == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.no_country_selected),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = stringResource(R.string.select_country_to_see_news),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            return
        }

        NewsSourceSelector(
            modifier = Modifier
                .padding(horizontal = 12.dp),
            selectedSource = uiState.selectedSource,
            onSourceSelected = { source ->
                onIntent(NewsUiIntent.SelectSource(source))
            }
        )

        val currentItemsCount = when (uiState.selectedSource) {
            Source.GOOGLE ->
                (uiState.google as? ResultState.Success)?.response?.data?.newsItems?.size ?: 0

            Source.REDDIT ->
                (uiState.reddit as? ResultState.Success)?.response?.data?.children?.size ?: 0

            Source.DELTA ->
                (uiState.deltaProject as? ResultState.Success)?.response?.articles?.size ?: 0
        }

        val (visibleCount, listState, isLoadingMore) =
            rememberUiPagination(totalItems = currentItemsCount)

        LaunchedEffect(uiState.selectedSource) {
            listState.scrollToItem(0)
        }

        when (uiState.selectedSource) {
            Source.GOOGLE -> {
                when (uiState.google) {

                    is ResultState.Loading -> {
                        SkeletonRenderer(SkeletonType.NEWS_DETAIL)
                    }

                    is ResultState.Success -> {
                        val items = uiState.google.response.data.newsItems

                        LazyColumn(state = listState) {
                            items(items.take(visibleCount)) { item ->
                                NewsCard(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp, horizontal = 12.dp),
                                    context = context,
                                    title = item.title,
                                    url = item.link,
                                    date = item.pubDate.toString(),
                                    author = item.source.name,
                                    source = Source.GOOGLE.label,
                                    isSelected = selectedUrl == item.link,
                                    onSelect = { onSelectUrl(item.link) }
                                )
                            }

                            if (isLoadingMore) {
                                item { LoadMoreFooter() }
                            }
                        }
                    }

                    is ResultState.Error -> {
                        ErrorRetryCard(
                            message = uiState.google.message,
                            onRetry = {
                                onIntent(NewsUiIntent.RetrySelectedSource)
                            }
                        )
                    }

                    else -> {
                        ErrorRetryCard(
                            message = stringResource(R.string.error_loading_news),
                            onRetry = {
                                onIntent(NewsUiIntent.RetrySelectedSource)
                            }
                        )
                    }
                }
            }

            Source.REDDIT -> {
                when (uiState.reddit) {
                    is ResultState.Loading -> SkeletonRenderer(SkeletonType.NEWS_DETAIL)

                    is ResultState.Success -> {
                        val items = uiState.reddit.response.data.children

                        LazyColumn(state = listState) {
                            items(items.take(visibleCount)) { post ->
                                NewsCard(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp, horizontal = 12.dp),
                                    context = context,
                                    title = post.data.selfText ?: "",
                                    url = post.data.url ?: "",
                                    date = post.data.createdAtMillis?.toDateText() ?: "",
                                    author = post.data.authorFullname ?: "",
                                    source = Source.REDDIT.label,
                                    isSelected = selectedUrl == post.data.url,
                                    onSelect = { onSelectUrl(post.data.url.toString()) }
                                )
                            }

                            if (isLoadingMore) {
                                item { LoadMoreFooter() }
                            }
                        }
                    }

                    else -> {}
                }
            }

            Source.DELTA -> {
                when (uiState.deltaProject) {
                    is ResultState.Loading -> SkeletonRenderer(SkeletonType.NEWS_DETAIL)

                    is ResultState.Success -> {
                        val items = uiState.deltaProject.response.articles

                        LazyColumn(state = listState) {
                            items(items.take(visibleCount)) { item ->
                                NewsCard(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp, horizontal = 12.dp),
                                    context = context,
                                    title = item.title,
                                    url = item.url,
                                    date = item.seenDate.toReadableDate(),
                                    author = item.sourceCountry,
                                    source = Source.DELTA.label,
                                    isSelected = selectedUrl == item.url,
                                    onSelect = { onSelectUrl(item.url) },
                                )
                            }

                            if (isLoadingMore) {
                                item { LoadMoreFooter() }
                            }
                        }
                    }

                    else -> {}
                }
            }

        }
    }
}

@Preview(
    name = "Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SelectedPreview() {
    InfoXPeruTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        val navController = rememberNavController()
        BaseContentLayout(
            isBodyScrollable = false,
            body = {
                NewsBody(
                    context = LocalContext.current,
                    selectedUrl = null,
                    onSelectUrl = {},
                    uiState = NewsUiState(
                        country = ResultState.Success(NewsMock().countryMock),
                        selectedCountry = null
                    ),
                    onCountrySelected = {},
                    onIntent = {}
                )
            },
            footer = {
                BottomNavigationBar(navController)
            }
        )
    }
}

@Preview(
    name = "Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SuccessPreview() {
    InfoXPeruTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        val navController = rememberNavController()
        BaseContentLayout(
            isBodyScrollable = false,
            body = {
                NewsBody(
                    context = LocalContext.current,
                    selectedUrl = null,
                    onSelectUrl = {},
                    uiState = NewsUiState(
                        country = ResultState.Success(NewsMock().countryMock),
                        selectedCountry = NewsMock().countryMock.data.first(),
                        google = ResultState.Success(NewsMock().googleMock),
                    ),
                    onCountrySelected = {},
                    onIntent = {},
                )
            },
            footer = {
                BottomNavigationBar(navController)
            }
        )
    }
}

@Preview(
    name = "Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ErrorPreview() {
    InfoXPeruTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        val navController = rememberNavController()
        BaseContentLayout(
            isBodyScrollable = false,
            body = {
                NewsBody(
                    context = LocalContext.current,
                    selectedUrl = null,
                    onSelectUrl = {},
                    uiState = NewsUiState(
                        country = ResultState.Success(NewsMock().countryMock),
                        selectedCountry = NewsMock().countryMock.data.first(),
                        selectedSource = Source.GOOGLE,
                        google = ResultState.Error("Error loading data"),
                    ),
                    onCountrySelected = {},
                    onIntent = {},
                )
            },
            footer = {
                BottomNavigationBar(navController)
            }
        )
    }
}