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
import androidx.compose.ui.graphics.Color
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
import com.devpaul.infoxperu.domain.screen.atomic.DividerView
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
            onCountrySelected = { category ->
                viewModel.getGoogleNews(query = category, language = "es")
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
    onCountrySelected: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        CountryCards(countryState = countryState, context = context, onCountrySelected = {
            selectedCategory = it
            onCountrySelected(it)
        })
        Spacer(modifier = Modifier.padding(8.dp))
        DividerView()
        if (selectedCategory == null) {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_sentiment_very_dissatisfied_24),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = "Seleccione una categoría",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Text(
                text = "Categoría seleccionada: $selectedCategory",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
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
            onCountrySelected = { }
        )
    }
}
