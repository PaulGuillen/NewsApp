package com.devpaul.infoxperu.feature.home.news_view

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.extension.logOut
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.ui.news_screen.CountryCards
import com.devpaul.infoxperu.domain.ui.utils.BottomNavigationBar
import com.devpaul.infoxperu.domain.ui.utils.TopBar

@Composable
fun NewsScreen(navController: NavHostController, viewModel: NewsViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val countryState by viewModel.countryState.collectAsState()

    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.app_name),
                onLogoutClick = {
                    logOut(navController)
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
        )
    }
}

@Composable
fun NewsContent(
    context: Context,
    modifier: Modifier = Modifier,
    countryState: ResultState<List<Country>>,
    innerPadding: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        CountryCards(countryState = countryState, context = context)
    }
}

@Preview(showBackground = true)
@Composable
fun NewsScreenPreview() {
    val navController = rememberNavController()

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
            innerPadding = innerPadding,
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
        )
    }
}