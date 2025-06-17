package com.devpaul.news.ui.news

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devpaul.core_platform.R
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.ui.news.components.country.CountryCards
import com.devpaul.news.ui.news.components.deltaproject.GDELTCards
import com.devpaul.news.ui.news.components.google.GoogleNewsCards
import com.devpaul.news.ui.news.components.reddit.RedditCards
import com.devpaul.shared.domain.handleDefaultErrors
import com.devpaul.shared.ui.components.atoms.DividerView
import com.devpaul.shared.ui.components.molecules.BottomNavigationBar
import com.devpaul.shared.ui.components.molecules.TopBar
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(navController: NavHostController) {

    val viewModel: NewsViewModel = koinViewModel()
    val context = LocalContext.current

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
        onDefaultError = { error, showSnackBar ->
            handleDefaultErrors(error, showSnackBar)
        },
        observeBackKeys = listOf("shouldReload"),
        onBackResults = { results, uiState, onIntent ->
            if (results["shouldReload"] == true && uiState.selectedCountry != null) {
                onIntent(NewsUiIntent.GetCountries)
                onIntent(NewsUiIntent.SelectCountry(uiState.selectedCountry))
            }
        },

        ) { _, uiState, onIntent, _, _ ->
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.app_name),
                    onLogoutClick = {
                        // viewModel.logOut(navController)
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
                uiState = uiState,
                onIntent = onIntent,
                onCountrySelected = { countryItem ->
                    onIntent(NewsUiIntent.SelectCountry(countryItem))
                }
            )
        }
    }
}


@Composable
fun NewsContent(
    context: Context,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    uiState: NewsUiState,
    onIntent: (NewsUiIntent) -> Unit,
    onCountrySelected: (CountryItemEntity) -> Unit
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
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
        DividerView()
        if (uiState.selectedCountry == null) {
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