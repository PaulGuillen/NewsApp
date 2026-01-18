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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
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
import com.devpaul.news.ui.news.components.country.CountryCards
import com.devpaul.news.ui.news.components.news.ErrorRetryCard
import com.devpaul.news.ui.news.components.news.NewsCard
import com.devpaul.news.ui.news.components.news.rememberUiPagination
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType
import com.devpaul.shared.ui.components.molecules.BottomNavigationBar
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import com.devpaul.shared.ui.components.organisms.LoadMoreFooter
import com.devpaul.shared.ui.components.organisms.coachmark.CoachMarkOverlay
import com.devpaul.shared.ui.components.organisms.coachmark.CoachMarkStep
import com.devpaul.shared.ui.components.organisms.coachmark.CoachMarkTargets
import com.devpaul.shared.ui.components.organisms.sourceselector.Source
import com.devpaul.shared.ui.components.organisms.sourceselector.SourceSelector
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(navController: NavHostController) {
    val viewModel: NewsViewModel = koinViewModel()
    val context = LocalContext.current
    val selectedUrl by viewModel.selectedUrl

    val coachMarkTargets = remember {
        mutableStateMapOf<String, LayoutCoordinates>()
    }

    val coachMarkSteps = remember {
        listOf(
            CoachMarkStep(
                id = "country",
                title = "Selecciona un país",
                description = "Elige el país para ver las noticias disponibles",
                targetKey = CoachMarkTargets.COUNTRY_SELECTOR
            ),
            CoachMarkStep(
                id = "source",
                title = "Selecciona la fuente",
                description = "Cambia entre Google, Reddit o Delta Project",
                targetKey = CoachMarkTargets.SOURCE_SELECTOR
            )
        )
    }

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController
    ) { _, uiState, onIntent, _, _ ->
        Box(modifier = Modifier.fillMaxSize()) {
            BaseContentLayout(
                isBodyScrollable = false,
                body = {
                    NewsBody(
                        context = context,
                        uiState = uiState,
                        onIntent = onIntent,
                        selectedUrl = selectedUrl,
                        onSelectUrl = viewModel::selectUrl,
                        coachMarkTargets = coachMarkTargets
                    )
                },
                footer = {
                    BottomNavigationBar(navController)
                }
            )

            val step = coachMarkSteps.getOrNull(uiState.coachMarkStepIndex)
            val target = step?.let { coachMarkTargets[it.targetKey] }

            if (uiState.showCoachMark && step != null && target != null) {
                CoachMarkOverlay(
                    target = target,
                    title = step.title,
                    description = step.description,
                    onNext = { onIntent(NewsUiIntent.NextCoachMark) },
                    onSkip = { onIntent(NewsUiIntent.SkipCoachMark) }
                )
            }
        }
    }
}

@Composable
fun NewsBody(
    context: Context,
    uiState: NewsUiState,
    onIntent: (NewsUiIntent) -> Unit,
    selectedUrl: String?,
    onSelectUrl: (String) -> Unit,
    coachMarkTargets: MutableMap<String, LayoutCoordinates>
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CountryCards(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        coachMarkTargets[CoachMarkTargets.COUNTRY_SELECTOR] = it
                    },
                countryState = uiState.country,
                selectedCountry = uiState.selectedCountry,
                onCountrySelected = {
                    onIntent(NewsUiIntent.SelectCountry(it))
                }
            )

            if (uiState.selectedCountry == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

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

            } else {
                SourceSelector(
                    modifier = Modifier
                        .onGloballyPositioned {
                            coachMarkTargets[CoachMarkTargets.SOURCE_SELECTOR] = it
                        }
                        .padding(4.dp),
                    selectedSource = uiState.selectedSource,
                    onSourceSelected = {
                        onIntent(NewsUiIntent.SelectSource(it))
                    }
                )

                val currentItemsCount = when (uiState.selectedSource) {
                    Source.GOOGLE ->
                        (uiState.google as? ResultState.Success)
                            ?.response?.data?.newsItems?.size ?: 0

                    Source.REDDIT ->
                        (uiState.reddit as? ResultState.Success)
                            ?.response?.data?.children?.size ?: 0

                    Source.DELTA ->
                        (uiState.deltaProject as? ResultState.Success)
                            ?.response?.articles?.size ?: 0
                }

                val (visibleCount, listState, isLoadingMore) =
                    rememberUiPagination(totalItems = currentItemsCount)

                LaunchedEffect(uiState.selectedSource) {
                    listState.scrollToItem(0)
                }

                when (uiState.selectedSource) {

                    Source.GOOGLE -> when (uiState.google) {

                        is ResultState.Loading ->
                            SkeletonRenderer(SkeletonType.NEWS_DETAIL)

                        is ResultState.Success -> {
                            LazyColumn(state = listState) {
                                items(
                                    uiState.google.response.data.newsItems
                                        .take(visibleCount)
                                ) { item ->
                                    NewsCard(
                                        modifier = Modifier.padding(10.dp),
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

                        is ResultState.Error ->
                            ErrorRetryCard(
                                message = uiState.google.message,
                                onRetry = {
                                    onIntent(NewsUiIntent.RetrySelectedSource)
                                }
                            )

                        is ResultState.Idle -> {}
                    }

                    Source.REDDIT -> when (uiState.reddit) {

                        is ResultState.Loading ->
                            SkeletonRenderer(SkeletonType.NEWS_DETAIL)

                        is ResultState.Success -> {
                            LazyColumn(state = listState) {
                                items(
                                    uiState.reddit.response.data.children
                                        .take(visibleCount)
                                ) { post ->
                                    NewsCard(
                                        modifier = Modifier.padding(12.dp),
                                        context = context,
                                        title = post.data.title ?: "",
                                        url = post.data.url ?: "",
                                        date = post.data.createdAtMillis?.toDateText() ?: "",
                                        author = post.data.authorFullname ?: "",
                                        source = Source.REDDIT.label,
                                        isSelected = selectedUrl == post.data.url,
                                        onSelect = {
                                            post.data.url?.let(onSelectUrl)
                                        }
                                    )
                                }
                                if (isLoadingMore) {
                                    item { LoadMoreFooter() }
                                }
                            }
                        }

                        is ResultState.Error ->
                            ErrorRetryCard(
                                message = uiState.reddit.message,
                                onRetry = {
                                    onIntent(NewsUiIntent.RetrySelectedSource)
                                }
                            )

                        is ResultState.Idle -> {}
                    }

                    Source.DELTA -> when (uiState.deltaProject) {

                        is ResultState.Loading ->
                            SkeletonRenderer(SkeletonType.NEWS_DETAIL)

                        is ResultState.Success -> {
                            LazyColumn(state = listState) {
                                items(
                                    uiState.deltaProject.response.articles
                                        .take(visibleCount)
                                ) { item ->
                                    NewsCard(
                                        modifier = Modifier.padding(12.dp),
                                        context = context,
                                        title = item.title,
                                        url = item.url,
                                        date = item.seenDate.toReadableDate(),
                                        author = item.sourceCountry,
                                        source = Source.DELTA.label,
                                        isSelected = selectedUrl == item.url,
                                        onSelect = { onSelectUrl(item.url) }
                                    )
                                }
                                if (isLoadingMore) {
                                    item { LoadMoreFooter() }
                                }
                            }
                        }

                        is ResultState.Error ->
                            ErrorRetryCard(
                                message = uiState.deltaProject.message,
                                onRetry = {
                                    onIntent(NewsUiIntent.RetrySelectedSource)
                                }
                            )

                        is ResultState.Idle -> {}
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SelectedPreview() {
    InfoXPeruTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        NewsBody(
            context = LocalContext.current,
            uiState = NewsUiState(
                country = ResultState.Success(NewsMock().countryMock)
            ),
            onIntent = {},
            selectedUrl = null,
            onSelectUrl = {},
            coachMarkTargets = mutableMapOf()
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
                    uiState = NewsUiState(
                        country = ResultState.Success(NewsMock().countryMock),
                        selectedCountry = NewsMock().countryMock.data.first(),
                        google = ResultState.Success(NewsMock().googleMock),
                    ),
                    onIntent = {},
                    selectedUrl = null,
                    onSelectUrl = {},
                    coachMarkTargets = mutableMapOf()
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
                    uiState = NewsUiState(
                        country = ResultState.Success(NewsMock().countryMock),
                        selectedCountry = NewsMock().countryMock.data.first(),
                        selectedSource = Source.GOOGLE,
                        google = ResultState.Error("Error loading data"),
                    ),

                    onIntent = {},
                    selectedUrl = null,
                    onSelectUrl = {},
                    coachMarkTargets = mutableMapOf()
                )
            },
            footer = {
                BottomNavigationBar(navController)
            }
        )
    }
}