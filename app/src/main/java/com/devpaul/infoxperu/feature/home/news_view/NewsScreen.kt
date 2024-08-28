package com.devpaul.infoxperu.feature.home.news_view

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
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.models.res.GoogleNewsJSON
import com.devpaul.infoxperu.domain.models.res.NewsItemJSON
import com.devpaul.infoxperu.domain.models.res.NewsSourceJSON
import com.devpaul.infoxperu.domain.screen.atomic.DividerView
import com.devpaul.infoxperu.domain.ui.news_screen.CountryCards
import com.devpaul.infoxperu.domain.ui.news_screen.GoogleNewsCards
import com.devpaul.infoxperu.domain.ui.utils.BottomNavigationBar
import com.devpaul.infoxperu.domain.ui.utils.TopBar

@Composable
fun NewsScreen(navController: NavHostController, viewModel: NewsViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val countryState by viewModel.countryState.collectAsState()
    val googleNews by viewModel.googleNewsState.collectAsState()
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
            modifier = Modifier.fillMaxSize(),
            countryState = countryState,
            innerPadding = innerPadding,
            googleNewsState = googleNews,
            selectedCountry = selectedCountry,
            onCountrySelected = { country ->
                selectedCountry = country
                viewModel.getGoogleNews(query = country.category, language = "es")
            }
        )
    }
}

@Composable
fun NewsContent(
    context: Context,
    modifier: Modifier = Modifier,
    countryState: ResultState<List<Country>>,
    innerPadding: PaddingValues = PaddingValues(),
    googleNewsState: ResultState<GoogleNewsJSON>,
    selectedCountry: Country?,
    onCountrySelected: (Country) -> Unit
) {

    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
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
                title = selectedCountry.title,
                googleNewsState = googleNewsState,
                context = context
            )
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
            selectedCountry = selectedCountry,
            onCountrySelected = { }
        )
    }
}
