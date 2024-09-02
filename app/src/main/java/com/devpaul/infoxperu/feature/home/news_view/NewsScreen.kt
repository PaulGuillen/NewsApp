package com.devpaul.infoxperu.feature.home.news_view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Article
import com.devpaul.infoxperu.domain.models.res.ArticleNewsResponse
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.domain.models.res.GoogleNewsJSON
import com.devpaul.infoxperu.domain.models.res.ListingData
import com.devpaul.infoxperu.domain.models.res.NewsItemJSON
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.domain.models.res.NewsSourceJSON
import com.devpaul.infoxperu.domain.models.res.PostData
import com.devpaul.infoxperu.domain.models.res.PostDataWrapper
import com.devpaul.infoxperu.domain.models.res.RedditResponse
import com.devpaul.infoxperu.domain.models.res.SourceResponse
import com.devpaul.infoxperu.domain.screen.atomic.DividerView
import com.devpaul.infoxperu.domain.ui.news_screen.CountryCards
import com.devpaul.infoxperu.domain.ui.news_screen.GDELTCards
import com.devpaul.infoxperu.domain.ui.news_screen.GoogleNewsCards
import com.devpaul.infoxperu.domain.ui.news_screen.NewsAPICards
import com.devpaul.infoxperu.domain.ui.news_screen.RedditCards
import com.devpaul.infoxperu.domain.ui.utils.BottomNavigationBar
import com.devpaul.infoxperu.domain.ui.utils.TopBar
import com.devpaul.infoxperu.ui.theme.SlateGray

@Composable
fun NewsScreen(navController: NavHostController, viewModel: NewsViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val countryState by viewModel.countryState.collectAsState()
    val googleNews by viewModel.googleNewsState.collectAsState()
    val projectGDELTNews by viewModel.projectGDELTState.collectAsState()
    val newsAPIState by viewModel.newsAPIState.collectAsState()
    val redditState by viewModel.redditState.collectAsState()

    var selectedCountry by remember { mutableStateOf<Country?>(null) }

    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.app_name),
                onLogoutClick = {
                    viewModel.logOut(navController)
                })
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NewsContent(
            context = context,
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            countryState = countryState,
            innerPadding = innerPadding,
            googleNewsState = googleNews,
            projectGDELTNews = projectGDELTNews,
            redditState = redditState,
            newsAPIState = newsAPIState,
            selectedCountry = selectedCountry,
            onCountrySelected = { country ->
                selectedCountry = country
                viewModel.getGoogleNews(query = country.category, language = "es")
                viewModel.getProjectGDELTNews(
                    query = country.category,
                    mode = "ArtList",
                    format = "json"
                )
                viewModel.getRedditNews(country = country.category)
                viewModel.getNewsAPI(initLetters = country.initLetters)
            }
        )
    }
}

@Composable
fun NewsContent(
    context: Context,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    countryState: ResultState<List<Country>>,
    innerPadding: PaddingValues = PaddingValues(),
    googleNewsState: ResultState<GoogleNewsJSON>,
    projectGDELTNews: ResultState<GDELProject>,
    redditState: ResultState<RedditResponse>,
    newsAPIState: ResultState<NewsResponse>,
    selectedCountry: Country?,
    onCountrySelected: (Country) -> Unit
) {

    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.padding(top = 16.dp))
        CountryCards(
            countryState = countryState,
            context = context,
            onCountrySelected = {
                onCountrySelected(it)
            }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        DividerView()
        if (selectedCountry == null) {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_country_selected),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = "Seleccione un país para ver las noticias",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        } else {
            GoogleNewsCards(
                navController = navController,
                selectedCountry = selectedCountry,
                googleNewsState = googleNewsState,
                context = context
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            Box(
                modifier = Modifier
                    .width(160.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(0.6.dp)
                    .background(SlateGray)
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))

            NewsAPICards(
                navController = navController,
                selectedCountry = selectedCountry,
                newsAPIState = newsAPIState,
                context = context
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            Box(
                modifier = Modifier
                    .width(160.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(0.6.dp)
                    .background(SlateGray)
            )

            GDELTCards(
                navController = navController,
                selectedCountry = selectedCountry,
                projectGDELTState = projectGDELTNews,
                context = context
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            Box(
                modifier = Modifier
                    .width(160.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(0.6.dp)
                    .background(SlateGray)
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            RedditCards(
                navController = navController,
                selectedCountry = selectedCountry,
                redditState = redditState,
                context = context
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsScreenPreviewWithOutCountrySelected() {
    val navController = rememberNavController()
    var selectedCountry by remember { mutableStateOf<Country?>(null) }

    Scaffold(
        topBar = {
            TopBar(title = "InfoPerú")
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NewsContent(
            context = LocalContext.current,
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            countryState = ResultState.Success(
                listOf(
                    Country(
                        title = "Peru",
                        category = "peru",
                        summary = "Resumen",
                        imageUrl = "https://www.google.com",
                    ),
                    Country(
                        title = "Argentina",
                        category = "argentina",
                        summary = "Resumen",
                        imageUrl = "https://www.google.com",
                    ),
                    Country(
                        title = "Ecuador",
                        category = "ecuador",
                        summary = "Resumen",
                        imageUrl = "https://www.google.com",
                    ),
                )
            ),
            innerPadding = innerPadding,
            googleNewsState = ResultState.Success(
                GoogleNewsJSON(
                    title = "Peru",
                    link = "https://www.google.com",
                    language = "es",
                    lastBuildDate = "2021-09-01",
                    description = "Descripción",
                    newsItems = emptyList()
                )
            ),
            projectGDELTNews = ResultState.Success(
                GDELProject(
                    listOf(
                        Article(
                            "https://www.deperu.com/tv/wQfz0Keo-Os.venezuela-vs-canada-penales-resumen-y-goles-copa-america-2024-libero.UCk2OZrA0E6q6xp4bBKtf9KA.html",
                            "",
                            "Video : ? VENEZUELA VS CANADÁ PENALES , RESUMEN Y GOLES - COPA AMÉRICA 2024",
                            "20240707T020000Z",
                            "https://i.ytimg.com/vi/wQfz0Keo-Os/hqdefault.jpg",
                            "deperu.com",
                            "Galician",
                            "United States",
                        )
                    )
                )
            ),
            redditState = ResultState.Success(
                RedditResponse(
                    kind = "Listing",
                    data = ListingData(
                        after = "123",
                        dist = 1,
                        modhash = "123",
                        geoFilter = "123",
                        children = listOf(
                            PostDataWrapper(
                                kind = "t3",
                                data = PostData(
                                    subreddit = "peru",
                                    title = "Peru",
                                    selfText = "Peru",
                                    authorFullname = "123",
                                    saved = false,
                                    gilded = 0,
                                    clicked = false,
                                    subredditNamePrefixed = "r/peru",
                                    hidden = false,
                                    pwls = 0,
                                    linkFlairCssClass = "123",
                                    downs = 0,
                                    hideScore = false,
                                    name = "123",
                                    quarantine = false,
                                    linkFlairTextColor = "123",
                                    upvoteRatio = 0.0,
                                    subredditType = "123",
                                    ups = 0,
                                    totalAwardsReceived = 0,
                                    isOriginalContent = false,
                                    userReports = emptyList(),
                                    secureMedia = null,
                                    isRedditMediaDomain = false,
                                    isMeta = false,
                                    category = "123",
                                    linkFlairText = "123",
                                    canModPost = false,
                                    score = 0,
                                    approvedBy = "123",
                                )
                            )
                        ),
                        before = "123"
                    )
                )
            ),
            newsAPIState = ResultState.Success(
                NewsResponse(
                    status = "ok",
                    totalResults = 1,
                    articles = listOf(
                        ArticleNewsResponse(
                            source = SourceResponse(
                                id = null,
                                name = "Infobae Perú"
                            ),
                            author = "Infobae Perú",
                            title = "Peru",
                            articleDescription = "Peru",
                            url = "https://www.google.com",
                            imageUrl = "https://www.google.com",
                            publishDate = "2021-09-01",
                            content = "Peru"
                        )
                    )
                )
            ),
            selectedCountry = selectedCountry,
            onCountrySelected = { selectedCountry = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsScreenPreviewWithCountrySelected() {
    val navController = rememberNavController()
    val selectedCountry = Country(
        title = "Peru",
        category = "peru",
        summary = "Resumen",
        imageUrl = "https://www.google.com",
    )

    Scaffold(
        topBar = {
            TopBar(title = "InfoPerú")
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NewsContent(
            context = LocalContext.current,
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            countryState = ResultState.Success(
                listOf(
                    Country(
                        title = "Peru",
                        category = "peru",
                        summary = "Resumen",
                        imageUrl = "https://www.google.com",
                    ),
                    Country(
                        title = "Argentina",
                        category = "argentina",
                        summary = "Resumen",
                        imageUrl = "https://www.google.com",
                    ),
                    Country(
                        title = "Ecuador",
                        category = "ecuador",
                        summary = "Resumen",
                        imageUrl = "https://www.google.com",
                    ),
                )
            ),
            innerPadding = innerPadding,
            googleNewsState = ResultState.Success(
                GoogleNewsJSON(
                    title = "Peru",
                    link = "https://www.google.com",
                    language = "es",
                    lastBuildDate = "2021-09-01",
                    description = "Descripción",
                    newsItems = listOf(
                        NewsItemJSON(
                            title = "Primero",
                            link = "https://www.google.com",
                            guid = "123",
                            pubDate = "2021-09-01",
                            description = "Descripción",
                            source = NewsSourceJSON(
                                url = "https://www.google.com",
                                name = "Infobae Perú"
                            )
                        ),
                        NewsItemJSON(
                            title = "Segundo",
                            link = "https://www.google.com",
                            guid = "123",
                            pubDate = "2021-09-01",
                            description = "Descripción",
                            source = NewsSourceJSON(
                                url = "https://www.google.com",
                                name = "Infobae Perú"
                            )
                        ),
                        NewsItemJSON(
                            title = "Tercero",
                            link = "https://www.google.com",
                            guid = "123",
                            pubDate = "2021-09-01",
                            description = "Descripción",
                            source = NewsSourceJSON(
                                url = "https://www.google.com",
                                name = "Infobae Perú"
                            )
                        )
                    )
                )
            ),
            projectGDELTNews = ResultState.Success(
                GDELProject(
                    listOf(
                        Article(
                            "https://www.deperu.com/tv/wQfz0Keo-Os.venezuela-vs-canada-penales-resumen-y-goles-copa-america-2024-libero.UCk2OZrA0E6q6xp4bBKtf9KA.html",
                            "",
                            "Video : ? VENEZUELA VS CANADÁ PENALES , RESUMEN Y GOLES - COPA AMÉRICA 2024",
                            "20240707T020000Z",
                            "https://i.ytimg.com/vi/wQfz0Keo-Os/hqdefault.jpg",
                            "deperu.com",
                            "Galician",
                            "United States",
                        )
                    )
                )
            ),
            redditState = ResultState.Success(
                RedditResponse(
                    kind = "Listing",
                    data = ListingData(
                        after = "123",
                        dist = 1,
                        modhash = "123",
                        geoFilter = "123",
                        children = listOf(
                            PostDataWrapper(
                                kind = "t3",
                                data = PostData(
                                    subreddit = "peru",
                                    title = "Peru",
                                    selfText = "Peru",
                                    authorFullname = "123",
                                    saved = false,
                                    gilded = 0,
                                    clicked = false,
                                    subredditNamePrefixed = "r/peru",
                                    hidden = false,
                                    pwls = 0,
                                    linkFlairCssClass = "123",
                                    downs = 0,
                                    hideScore = false,
                                    name = "123",
                                    quarantine = false,
                                    linkFlairTextColor = "123",
                                    upvoteRatio = 0.0,
                                    subredditType = "123",
                                    ups = 0,
                                    totalAwardsReceived = 0,
                                    isOriginalContent = false,
                                    userReports = emptyList(),
                                    secureMedia = null,
                                    isRedditMediaDomain = false,
                                    isMeta = false,
                                    category = "123",
                                    linkFlairText = "123",
                                    canModPost = false,
                                    score = 0,
                                    approvedBy = "123",
                                )
                            )
                        ),
                        before = "123"
                    )
                )
            ),
            newsAPIState = ResultState.Success(
                NewsResponse(
                    status = "ok",
                    totalResults = 1,
                    articles = listOf(
                        ArticleNewsResponse(
                            source = SourceResponse(
                                id = null,
                                name = "Infobae Perú"
                            ),
                            author = "Infobae Perú",
                            title = "Peru",
                            articleDescription = "Peru",
                            url = "https://www.google.com",
                            imageUrl = "https://www.google.com",
                            publishDate = "2021-09-01",
                            content = "Peru"
                        )
                    )
                )
            ),
            selectedCountry = selectedCountry,
            onCountrySelected = { }
        )
    }
}
