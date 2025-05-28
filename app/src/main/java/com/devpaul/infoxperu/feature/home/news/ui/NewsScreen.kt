package com.devpaul.infoxperu.feature.home.news.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.devpaul.infoxperu.core.mocks.CountryMock
import com.devpaul.infoxperu.core.mocks.GDELProjectMock
import com.devpaul.infoxperu.core.mocks.GoogleNewsMock
import com.devpaul.infoxperu.core.mocks.NewsMock
import com.devpaul.infoxperu.core.mocks.RedditMock
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.screen.atomic.DividerView
import com.devpaul.infoxperu.feature.home.news.ui.components.CountryCards
import com.devpaul.infoxperu.feature.home.news.ui.components.GDELTCards
import com.devpaul.infoxperu.feature.home.news.ui.components.GoogleNewsCards
import com.devpaul.infoxperu.feature.home.news.ui.components.NewsAPICards
import com.devpaul.infoxperu.feature.home.news.ui.components.RedditCards
import com.devpaul.infoxperu.feature.util.Constant
import com.devpaul.infoxperu.feature.util.ui.utils.BottomNavigationBar
import com.devpaul.infoxperu.feature.util.ui.utils.TopBar

@Composable
fun NewsScreen(navController: NavHostController, viewModel: NewsViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val countryState by viewModel.countryState.collectAsState()
    val googleNews by viewModel.googleNewsState.collectAsState()
    val projectGDELTNews by viewModel.projectGDELTState.collectAsState()
    val newsAPIState by viewModel.newsAPIState.collectAsState()
    val redditState by viewModel.redditState.collectAsState()

    var selectedCountry by remember { mutableStateOf<Country?>(null) }

    LaunchedEffect(Unit) {
        viewModel.executeUiIntent(NewsUiIntent.Country)
    }

    val state = NewsUiState(
        countryState = countryState,
        googleNewsState = googleNews,
        projectGDELTState = projectGDELTNews,
        redditState = redditState,
        newsAPIState = newsAPIState,
        selectedCountry = selectedCountry,
    )

    val callbacks = NewsContentCallbacks(
        onCountrySelected = { country ->
            selectedCountry = country
            viewModel.getGoogleNews(
                query = country.category,
                language = Constant.NEWS_LANGUAGE,
                limit = 20,
            )
            viewModel.getProjectGDELTNews(
                limit = 10,
                query = country.category,
                mode = Constant.NEWS_MODE,
                format = Constant.NEWS_FORMAT,
            )
            viewModel.getRedditNews(limit = 20, country = country.category)
            viewModel.getNewsAPI(limit = 20, initLetters = country.initLetters)
        }
    )

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.app_name),
                onLogoutClick = {
                    viewModel.logOut(navController)
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NewsContent(
            context = context,
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            state = state,
            callbacks = callbacks,
        )
    }
}

@Composable
fun NewsContent(
    context: Context,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(),
    state: NewsUiState,
    callbacks: NewsContentCallbacks,
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.padding(top = 16.dp))
        CountryCards(
            countryState = state.countryState,
            context = context,
            onCountrySelected = callbacks.onCountrySelected,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        DividerView()

        if (state.selectedCountry == null) {
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
                    text = stringResource(R.string.select_country_to_see_news),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        } else {
            GoogleNewsCards(
                navController = navController,
                selectedCountry = state.selectedCountry,
                googleNewsState = state.googleNewsState,
                context = context,
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))

            NewsAPICards(
                navController = navController,
                selectedCountry = state.selectedCountry,
                newsAPIState = state.newsAPIState,
                context = context,
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))

            GDELTCards(
                navController = navController,
                selectedCountry = state.selectedCountry,
                projectGDELTState = state.projectGDELTState,
                context = context,
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))

            RedditCards(
                navController = navController,
                selectedCountry = state.selectedCountry,
                redditState = state.redditState,
                context = context,
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

    val state = NewsUiState(
        countryState = ResultState.Success(data = CountryMock().countryListMock),
        googleNewsState = ResultState.Success(data = GoogleNewsMock().googleNewsMock),
        projectGDELTState = ResultState.Success(data = GDELProjectMock().deltaProjectMock),
        redditState = ResultState.Success(data = RedditMock().redditMock),
        newsAPIState = ResultState.Success(data = NewsMock().newsMock),
        selectedCountry = selectedCountry,
    )

    val callbacks = NewsContentCallbacks(
        onCountrySelected = { selectedCountry = it }
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
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            state = state,
            callbacks = callbacks,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsScreenPreviewWithCountrySelected() {
    val navController = rememberNavController()
    val selectedCountry = CountryMock().countryListMock.first()

    val state = NewsUiState(
        countryState = ResultState.Success(data = CountryMock().countryListMock),
        googleNewsState = ResultState.Success(data = GoogleNewsMock().googleNewsMock),
        projectGDELTState = ResultState.Success(data = GDELProjectMock().deltaProjectMock),
        redditState = ResultState.Success(data = RedditMock().redditMock),
        newsAPIState = ResultState.Success(data = NewsMock().newsMock),
        selectedCountry = selectedCountry,
    )

    val callbacks = NewsContentCallbacks(
        onCountrySelected = { }
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
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            state = state,
            callbacks = callbacks,
        )
    }
}