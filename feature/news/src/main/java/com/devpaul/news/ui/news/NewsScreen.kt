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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devpaul.core_platform.R
import com.devpaul.navigation.core.jetpack.AppNavigator
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.ui.news.components.country.CountryCards
import com.devpaul.news.ui.news.components.deltaproject.GDELTCards
import com.devpaul.news.ui.news.components.google.GoogleNewsCards
import com.devpaul.news.ui.news.components.reddit.RedditCards
import com.devpaul.shared.extension.handleDefaultErrors
import com.devpaul.shared.screen.BaseScreenWithState
import com.devpaul.shared.screen.atomic.DividerView
import com.devpaul.shared.ui.extension.BottomNavigationBar
import com.devpaul.shared.ui.extension.TopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(navController: NavHostController) {

    val viewModel: NewsViewModel = koinViewModel()
    val context = LocalContext.current
    val uiModel = remember { mutableStateOf(NewsUiModel()) }

    BaseScreenWithState(
        viewModel = viewModel,
        onUiEvent = { event, _ ->
            when (event) {
                is NewsUiEvent.CountrySuccess -> {
                    uiModel.value = uiModel.value.copy(country = event.response)
                }

                is NewsUiEvent.CountryError -> {
                    uiModel.value =
                        uiModel.value.copy(countryError = event.error.apiErrorResponse?.message)
                }

                is NewsUiEvent.GoogleSuccess -> {
                    uiModel.value = uiModel.value.copy(google = event.response)
                }

                is NewsUiEvent.GoogleError -> {
                    uiModel.value =
                        uiModel.value.copy(googleError = event.error.apiErrorResponse?.message)
                }

                is NewsUiEvent.DeltaProjectSuccess -> {
                    uiModel.value = uiModel.value.copy(deltaProject = event.response)
                }

                is NewsUiEvent.DeltaProjectError -> {
                    uiModel.value =
                        uiModel.value.copy(deltaProjectError = event.error.apiErrorResponse?.message)
                }

                is NewsUiEvent.RedditSuccess -> {
                    uiModel.value = uiModel.value.copy(reddit = event.response)
                }

                is NewsUiEvent.RedditError -> {
                    uiModel.value =
                        uiModel.value.copy(redditError = event.error.apiErrorResponse?.message)
                }
            }
        },
        onDefaultError = { error, showSnackBar ->
            handleDefaultErrors(error, showSnackBar)
        }
    ) { _, uiState, onIntent, _ ->
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
                uiModel = uiModel.value,
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
    uiModel: NewsUiModel,
    onCountrySelected: (CountryItemEntity) -> Unit
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.padding(top = 16.dp))

        CountryCards(
            country = uiModel.country,
            countryError = uiModel.countryError,
            countryLoading = uiState.isCountryLoading,
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
                selectedCountry = uiState.selectedCountry,
                google = uiModel.google,
                googleError = uiModel.googleError,
                googleLoading = uiState.isGoogleLoading,
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            RedditCards(
                navController = navController,
                context = context,
                selectedCountry = uiState.selectedCountry,
                reddit = uiModel.reddit,
                redditError = uiModel.redditError,
                redditLoading = uiState.isRedditLoading,
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            GDELTCards(
                navController = navController,
                context = context,
                selectedCountry = uiState.selectedCountry,
                deltaProject = uiModel.deltaProject,
                deltaProjectError = uiModel.deltaProjectError,
                deltaProjectLoading = uiState.isDeltaProjectLoading,
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
        }
    }
}