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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
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
import com.devpaul.news.ui.news.components.news.DropdownDialog
import com.devpaul.news.ui.news.components.news.DropdownSelector
import com.devpaul.news.ui.news.components.news.LoadMoreFooter
import com.devpaul.news.ui.news.components.country.CountryCards
import com.devpaul.news.ui.news.components.news.rememberUiPagination
import com.devpaul.news.ui.news_detail.components.NewsCard
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType
import com.devpaul.shared.ui.components.molecules.BottomNavigationBar
import com.devpaul.shared.ui.components.molecules.TopBar
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
    onCountrySelected: (CountryItemEntity) -> Unit,
    selectedUrl: String?,
    onSelectUrl: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.padding(top = 8.dp))

        CountryCards(
            countryState = uiState.country,
            selectedCountry = uiState.selectedCountry,
            onCountrySelected = onCountrySelected
        )

        Spacer(modifier = Modifier.padding(8.dp))

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            thickness = 1.5.dp,
            color = MaterialTheme.colorScheme.outline
        )

        if (uiState.selectedCountry == null) {
            Spacer(modifier = Modifier.height(80.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.no_country_selected),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = stringResource(R.string.select_country_to_see_news),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            return
        }

        var selectedNew by remember { mutableStateOf<String?>(null) }
        var showDialog by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            DropdownSelector(
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 12.dp)
                    .width(140.dp),
                selectedValue = selectedNew,
                onClick = { showDialog = true }
            )
        }

        if (showDialog) {
            DropdownDialog(
                title = "Selecciona fuente",
                onDismiss = { showDialog = false },
                onItemSelected = {
                    selectedNew = it
                    showDialog = false
                }
            )
        }

        val currentItemsCount = when (selectedNew) {
            "Google News" -> uiState.google.let {
                if (it is ResultState.Success) it.response.data.newsItems.size else 0
            }
            "Delta Project" -> uiState.deltaProject.let {
                if (it is ResultState.Success) it.response.articles.size else 0
            }
            "Reddit" -> uiState.reddit.let {
                if (it is ResultState.Success) it.response.data.children.size else 0
            }
            else -> 0
        }

        val (visibleCount, listState, isLoadingMore) =
            rememberUiPagination(totalItems = currentItemsCount)

        LaunchedEffect(selectedNew) {
            listState.scrollToItem(0)
        }

        when (selectedNew) {
            "Google News" -> {
                when (uiState.google) {

                    is ResultState.Loading -> {
                        SkeletonRenderer(SkeletonType.NEWS_DETAIL)
                    }

                    is ResultState.Success -> {
                        val items = uiState.google.response.data.newsItems

                        LazyColumn(state = listState) {
                            items(items.take(visibleCount)) { item ->
                                NewsCard(
                                    context = context,
                                    title = item.title,
                                    summary = item.description.toString(),
                                    url = item.link,
                                    date = item.pubDate.toString(),
                                    author = item.source.name,
                                    isSelected = selectedUrl == item.link,
                                    onSelect = { onSelectUrl(item.link) }
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


            "Delta Project" -> {
                when (uiState.deltaProject) {
                    is ResultState.Loading -> SkeletonRenderer(SkeletonType.NEWS_DETAIL)

                    is ResultState.Success -> {
                        val items = uiState.deltaProject.response.articles

                        LazyColumn(state = listState) {
                            items(items.take(visibleCount)) { item ->
                                NewsCard(
                                    context = context,
                                    title = item.title,
                                    summary = item.domain,
                                    url = item.url,
                                    date = item.seenDate.toReadableDate(),
                                    author = item.sourceCountry,
                                    isSelected = selectedUrl == item.url,
                                    onSelect = { onSelectUrl(item.url) }
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

            "Reddit" -> {
                when (uiState.reddit) {
                    is ResultState.Loading -> SkeletonRenderer(SkeletonType.NEWS_DETAIL)

                    is ResultState.Success -> {
                        val items = uiState.reddit.response.data.children

                        LazyColumn(state = listState) {
                            items(items.take(visibleCount)) { post ->
                                NewsCard(
                                    context = context,
                                    title = post.data.title ?: "",
                                    summary = post.data.selfText ?: "",
                                    url = post.data.url ?: "",
                                    date = post.data.createdAtMillis?.toDateText() ?: "",
                                    author = post.data.authorFullname ?: "",
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsBodyPreview() {
    NewsBody(
        context = LocalContext.current,
        selectedUrl = null,
        onSelectUrl = {},
        uiState = NewsUiState(
            country = ResultState.Success(NewsMock().countryMock),
            selectedCountry = CountryItemEntity(
                id = "1",
                summary = "",
                title = "Perú",
                category = "",
                imageUrl = "https://example.com/image.png",
                initLetters = "P",
            )
        ),
        onCountrySelected = {}
    )
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
fun NewsScreenPreview() {
    InfoXPeruTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        val navController = rememberNavController()
        BaseContentLayout(
            isBodyScrollable = false,
            header = {
                TopBar(
                    title = "Home",
                )
            },
            body = {
                NewsBody(
                    context = LocalContext.current,
                    selectedUrl = null,
                    onSelectUrl = {},
                    uiState = NewsUiState(
                        country = ResultState.Success(NewsMock().countryMock),
                        selectedCountry = null
                    ),
                    onCountrySelected = {}
                )
            },
            footer = {
                BottomNavigationBar(navController)
            }
        )
    }
}