package com.devpaul.infoxperu.feature.home.services.ui.district_screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.District
import com.devpaul.infoxperu.feature.util.ui.service_screen.DistrictGrid
import com.devpaul.infoxperu.feature.util.ui.utils.BottomNavigationBar
import com.devpaul.infoxperu.feature.util.ui.utils.TopBarWithSearch

@Composable
fun DistrictsScreen(
    viewModel: DistrictViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val districtState by viewModel.districtState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBarWithSearch(
                title = stringResource(R.string.app_name),
                searchQuery = searchQuery,
                onSearchQueryChanged = { searchQuery = it }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }

    ) { innerPadding ->
        DistrictScreenContent(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            innerPadding = innerPadding,
            context = context,
            districtState = districtState,
            searchQuery = searchQuery
        )
    }
}

@Composable
fun DistrictScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues = PaddingValues(),
    context: Context,
    districtState: ResultState<List<District>>,
    searchQuery: String
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
    ) {
        DistrictGrid(
            navController = navController,
            searchQuery = searchQuery,
            context = context,
            districtState = districtState,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ServiceScreenPreview() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopBarWithSearch(title = "InfoPerú", "", onSearchQueryChanged = {})
        }
    ) { innerPadding ->
        DistrictScreenContent(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            innerPadding = innerPadding,
            context = LocalContext.current,
            searchQuery = "",
            districtState = ResultState.Success(
                listOf(
                    District("Lima", "lima")
                )
            ),
        )
    }
}

