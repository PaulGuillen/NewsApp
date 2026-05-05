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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
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
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.news.data.datasource.mock.NewsMock
import com.devpaul.news.ui.news.components.country.CountryCards
import com.devpaul.news.ui.news.components.news.ErrorRetryCard
import com.devpaul.news.ui.news.components.news.NewsCard
import com.devpaul.news.ui.news.components.news.rememberUiPagination
import com.devpaul.news.ui.news.components.news_section.SourceSection
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType
import com.devpaul.shared.domain.formatDeltaTime
import com.devpaul.shared.domain.formatRedditTime
import com.devpaul.shared.domain.getRelativeTime
import com.devpaul.shared.ui.components.molecules.AppHeader
import com.devpaul.shared.ui.components.molecules.HomeBottomBar
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import com.devpaul.shared.ui.components.organisms.LoadMoreFooter
import com.devpaul.shared.ui.components.organisms.coachmark.CoachMarkOverlay
import com.devpaul.shared.ui.components.organisms.coachmark.CoachMarkStep
import com.devpaul.shared.ui.components.organisms.coachmark.CoachMarkTargets
import com.devpaul.shared.ui.components.organisms.sourceselector.Source
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
                header = {
                    NewsHeader()
                },
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
                    HomeBottomBar(navController)
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
fun NewsHeader() {
    AppHeader(
        title = "Emergencias PE",
        subtitle = "Lunes, 24 de Mayo",
        icon = Icons.Default.Public,
        onNotificationClick = { }
    )
}

@Composable
private fun NewsBody(
    context: Context,
    uiState: NewsUiState,
    onIntent: (NewsUiIntent) -> Unit,
    selectedUrl: String?,
    onSelectUrl: (String) -> Unit,
    coachMarkTargets: MutableMap<String, LayoutCoordinates>
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CountrySection(
            uiState = uiState,
            onIntent = onIntent,
            coachMarkTargets = coachMarkTargets
        )

        when {
            uiState.selectedCountry == null -> {
                EmptyCountryState()
            }

            else -> {
                SourceSection(
                    uiState = uiState,
                    onIntent = onIntent,
                )

                NewsContent(
                    context = context,
                    uiState = uiState,
                    onIntent = onIntent,
                    selectedUrl = selectedUrl,
                    onSelectUrl = onSelectUrl
                )
            }
        }
    }
}

@Composable
private fun CountrySection(
    uiState: NewsUiState,
    onIntent: (NewsUiIntent) -> Unit,
    coachMarkTargets: MutableMap<String, LayoutCoordinates>
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
}

@Composable
private fun EmptyCountryState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(R.drawable.no_country_selected),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.select_country_to_see_news),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun NewsContent(
    context: Context,
    uiState: NewsUiState,
    onIntent: (NewsUiIntent) -> Unit,
    selectedUrl: String?,
    onSelectUrl: (String) -> Unit
) {
    val totalItems = remember(uiState.selectedSource, uiState) {
        when (uiState.selectedSource) {
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
    }

    val (visibleCount, listState, isLoadingMore) =
        rememberUiPagination(totalItems)

    LaunchedEffect(uiState.selectedSource) {
        listState.scrollToItem(0)
    }

    when (uiState.selectedSource) {
        Source.GOOGLE -> GoogleNewsContent(
            context,
            uiState,
            onIntent,
            listState,
            visibleCount,
            isLoadingMore,
            selectedUrl,
            onSelectUrl
        )

        Source.REDDIT -> RedditNewsContent(
            context,
            uiState,
            onIntent,
            listState,
            visibleCount,
            isLoadingMore,
            selectedUrl,
            onSelectUrl
        )

        Source.DELTA -> DeltaNewsContent(
            context,
            uiState,
            onIntent,
            listState,
            visibleCount,
            isLoadingMore,
            selectedUrl,
            onSelectUrl
        )
    }
}

@Composable
private fun GoogleNewsContent(
    context: Context,
    uiState: NewsUiState,
    onIntent: (NewsUiIntent) -> Unit,
    listState: androidx.compose.foundation.lazy.LazyListState,
    visibleCount: Int,
    isLoadingMore: Boolean,
    selectedUrl: String?,
    onSelectUrl: (String) -> Unit
) {
    when (uiState.google) {
        is ResultState.Loading ->
            SkeletonRenderer(SkeletonType.NEWS_DETAIL)

        is ResultState.Success -> {
            LazyColumn(state = listState) {
                items(uiState.google.response.data.newsItems.take(visibleCount)) { item ->
                    NewsCard(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                        context = context,
                        title = item.title,
                        source = Source.GOOGLE.label,
                        category = item.source.name,
                        time = getRelativeTime(item.pubDateMillis),
                        url = item.link,
                        isSelected = selectedUrl == item.link,
                        onSelect = {
                            onSelectUrl(item.link)
                        }
                    )
                }
                if (isLoadingMore) item { LoadMoreFooter() }
            }
        }

        is ResultState.Error ->
            ErrorRetryCard(
                message = uiState.google.message,
                onRetry = { onIntent(NewsUiIntent.RetrySelectedSource) }
            )

        ResultState.Idle -> {}
    }
}

@Composable
private fun RedditNewsContent(
    context: Context,
    uiState: NewsUiState,
    onIntent: (NewsUiIntent) -> Unit,
    listState: androidx.compose.foundation.lazy.LazyListState,
    visibleCount: Int,
    isLoadingMore: Boolean,
    selectedUrl: String?,
    onSelectUrl: (String) -> Unit
) {
    when (uiState.reddit) {
        is ResultState.Loading ->
            SkeletonRenderer(SkeletonType.NEWS_DETAIL)

        is ResultState.Success -> {
            LazyColumn(state = listState) {
                items(uiState.reddit.response.data.children.take(visibleCount)) { post ->
                    val item = post.data
                    NewsCard(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                        context = context,
                        title = item.title ?: "",
                        source = Source.REDDIT.label,
                        category = item.subreddit ?: "Reddit",
                        time = formatRedditTime(item.createdAtMillis),
                        url = item.url ?: "",
                        isSelected = selectedUrl == item.url,
                        onSelect = {
                            item.url?.let(onSelectUrl)
                        }
                    )
                }
                if (isLoadingMore) item { LoadMoreFooter() }
            }
        }

        is ResultState.Error ->
            ErrorRetryCard(
                message = uiState.reddit.message,
                onRetry = { onIntent(NewsUiIntent.RetrySelectedSource) }
            )

        ResultState.Idle -> {}
    }
}

@Composable
private fun DeltaNewsContent(
    context: Context,
    uiState: NewsUiState,
    onIntent: (NewsUiIntent) -> Unit,
    listState: androidx.compose.foundation.lazy.LazyListState,
    visibleCount: Int,
    isLoadingMore: Boolean,
    selectedUrl: String?,
    onSelectUrl: (String) -> Unit
) {
    when (uiState.deltaProject) {
        is ResultState.Loading ->
            SkeletonRenderer(SkeletonType.NEWS_DETAIL)

        is ResultState.Success -> {
            LazyColumn(state = listState) {
                items(uiState.deltaProject.response.articles.take(visibleCount)) { item ->
                    val safeUrl = item.url
                    NewsCard(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                        context = context,
                        title = item.title,
                        source = Source.DELTA.label,
                        category = item.sourceCountry,
                        time = formatDeltaTime(item.seenDate),
                        url = safeUrl,
                        isSelected = selectedUrl == safeUrl,
                        onSelect = {
                            onSelectUrl(safeUrl)
                        }
                    )
                }
                if (isLoadingMore) item { LoadMoreFooter() }
            }
        }

        is ResultState.Error ->
            ErrorRetryCard(
                message = uiState.deltaProject.message,
                onRetry = { onIntent(NewsUiIntent.RetrySelectedSource) }
            )

        ResultState.Idle -> {}
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
fun NewsScreenPreview() {
    InfoXPeruTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        BaseContentLayout(
            isBodyScrollable = false,
            header = {
                NewsHeader()
            },
            body = {
                NewsBody(
                    context = LocalContext.current,
                    uiState = NewsUiState(
                        country = ResultState.Success(NewsMock().countryMock),
                        selectedCountry = NewsMock().countryMock.data.first(),
                        selectedSource = Source.GOOGLE,
                        google = ResultState.Success(NewsMock().googleMock)
                    ),
                    onIntent = {},
                    selectedUrl = null,
                    onSelectUrl = {},
                    coachMarkTargets = mutableMapOf()
                )
            },
            footer = {
                HomeBottomBar(rememberNavController())
            },
        )
    }
}

@Preview(
    name = "CoachMark - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "CoachMark - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CoachMarkOverlayPreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        Box(modifier = Modifier.fillMaxSize()) {
            val coachMarkTargets = remember { mutableStateMapOf<String, LayoutCoordinates>() }

            Box(
                modifier = Modifier
                    .padding(top = 120.dp)
                    .size(160.dp)
                    .onGloballyPositioned { coords ->
                        coachMarkTargets[CoachMarkTargets.COUNTRY_SELECTOR] = coords
                    }
                    .align(Alignment.TopCenter)
            ) {
                Text(text = "TARGET", style = MaterialTheme.typography.bodyMedium)
            }

            val target = coachMarkTargets[CoachMarkTargets.COUNTRY_SELECTOR]
            if (target != null) {
                CoachMarkOverlay(
                    target = target,
                    title = "Selecciona un país",
                    description = "Elige el país para ver las noticias disponibles",
                    onNext = {},
                    onSkip = {}
                )
            }
        }
    }
}

@Preview(
    name = "News - Loading",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "News - Loading Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun NewsScreenLoadingPreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        BaseContentLayout(
            isBodyScrollable = false,
            header = { NewsHeader() },
            body = {
                NewsBody(
                    context = LocalContext.current,
                    uiState = NewsUiState(
                        country = ResultState.Loading,
                        selectedCountry = null,
                        selectedSource = Source.GOOGLE,
                        google = ResultState.Loading,
                        reddit = ResultState.Loading,
                        deltaProject = ResultState.Loading
                    ),
                    onIntent = {},
                    selectedUrl = null,
                    onSelectUrl = {},
                    coachMarkTargets = mutableMapOf()
                )
            },
            footer = { HomeBottomBar(rememberNavController()) }
        )
    }
}

@Preview(
    name = "News - Empty Country",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "News - Empty Country Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun NewsScreenEmptyCountryPreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        BaseContentLayout(
            isBodyScrollable = false,
            header = { NewsHeader() },
            body = {
                NewsBody(
                    context = LocalContext.current,
                    uiState = NewsUiState(
                        country = ResultState.Success(NewsMock().countryMock),
                        selectedCountry = null,
                        selectedSource = Source.GOOGLE
                    ),
                    onIntent = {},
                    selectedUrl = null,
                    onSelectUrl = {},
                    coachMarkTargets = mutableMapOf()
                )
            },
            footer = { HomeBottomBar(rememberNavController()) }
        )
    }
}

@Preview(
    name = "News - With CoachMark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "News - With CoachMark Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun NewsScreenWithCoachMarkPreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Build a full NewsBody with coachmark visible; the preview will render the overlay
            val coachMarkTargets = remember { mutableStateMapOf<String, LayoutCoordinates>() }

            BaseContentLayout(
                isBodyScrollable = false,
                header = { NewsHeader() },
                body = {
                    NewsBody(
                        context = LocalContext.current,
                        uiState = NewsUiState(
                            country = ResultState.Success(NewsMock().countryMock),
                            selectedCountry = NewsMock().countryMock.data.first(),
                            selectedSource = Source.GOOGLE,
                            google = ResultState.Success(NewsMock().googleMock),
                            showCoachMark = true,
                            coachMarkStepIndex = 0
                        ),
                        onIntent = {},
                        selectedUrl = null,
                        onSelectUrl = {},
                        coachMarkTargets = coachMarkTargets
                    )
                },
                footer = { HomeBottomBar(rememberNavController()) }
            )

            // place a target box so overlay has coordinates in preview
            Box(
                modifier = Modifier
                    .padding(top = 120.dp)
                    .size(160.dp)
                    .onGloballyPositioned { coords ->
                        coachMarkTargets[CoachMarkTargets.COUNTRY_SELECTOR] = coords
                    }
                    .align(Alignment.TopCenter)
            ) {}
            val target = coachMarkTargets[CoachMarkTargets.COUNTRY_SELECTOR]
            if (target != null) {
                CoachMarkOverlay(
                    target = target,
                    title = "Selecciona un país",
                    description = "Elige el país para ver las noticias disponibles",
                    onNext = {},
                    onSkip = {}
                )
            }
        }
    }
}

