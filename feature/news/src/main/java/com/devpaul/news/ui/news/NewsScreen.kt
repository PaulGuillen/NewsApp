package com.devpaul.news.ui.news

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.news.data.datasource.mock.NewsMock
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.ui.news.components.country.CountryCards
import com.devpaul.news.ui.news.components.deltaproject.GDELTCards
import com.devpaul.news.ui.news.components.google.GoogleNewsCards
import com.devpaul.news.ui.news.components.reddit.RedditCards
import com.devpaul.shared.ui.components.molecules.BottomNavigationBar
import com.devpaul.shared.ui.components.molecules.TopBarPrincipal
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(navController: NavHostController) {

    val viewModel: NewsViewModel = koinViewModel()
    val context = LocalContext.current

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
            isBodyScrollable = true,
            header = {
                TopBarPrincipal(
                    style = 3,
                    title = stringResource(R.string.header_actually)
                )
            },
            body = {
                NewsBody(
                    context = context,
                    navController = navController,
                    uiState = uiState,
                    onIntent = onIntent,
                    onCountrySelected = { countryItem ->
                        onIntent(NewsUiIntent.SelectCountry(countryItem))
                    }
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
    navController: NavHostController,
    uiState: NewsUiState,
    onIntent: (NewsUiIntent) -> Unit,
    onCountrySelected: (CountryItemEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(top = 16.dp))

        CountryCards(
            countryState = uiState.country,
            selectedCountry = uiState.selectedCountry,
            onCountrySelected = { countryItem ->
                onCountrySelected(countryItem)
            }
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
            Column(
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
        } else {
            GoogleNewsCards(
                navController = navController,
                context = context,
                googleState = uiState.google,
                selectedCountry = uiState.selectedCountry,
                onRetry = {
                    onIntent(NewsUiIntent.GetGoogleNews(uiState.selectedCountry))
                }
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            RedditCards(
                navController = navController,
                context = context,
                redditState = uiState.reddit,
                selectedCountry = uiState.selectedCountry,
                onRetry = {
                    onIntent(NewsUiIntent.GetReddit(uiState.selectedCountry))
                }
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            GDELTCards(
                navController = navController,
                context = context,
                deltaProjectState = uiState.deltaProject,
                selectedCountry = uiState.selectedCountry,
                onRetry = {
                    onIntent(NewsUiIntent.GetDeltaProject(uiState.selectedCountry))
                }
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsBodyPreview() {
    NewsBody(
        context = LocalContext.current,
        navController = NavHostController(LocalContext.current),
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
        onIntent = {},
        onCountrySelected = {}
    )
}

@Preview(showBackground = true)
@Composable
fun NewsBodyNoSelectedCountryPreview() {
    NewsBody(
        context = LocalContext.current,
        navController = NavHostController(LocalContext.current),
        uiState = NewsUiState(
            country = ResultState.Success(NewsMock().countryMock),
            selectedCountry = null
        ),
        onIntent = {},
        onCountrySelected = {}
    )
}