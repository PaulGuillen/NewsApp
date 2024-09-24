package com.devpaul.infoxperu.domain.ui.contacts_screen.district_screen

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.devpaul.infoxperu.domain.models.res.District
import com.devpaul.infoxperu.domain.ui.utils.TopBar

@Composable
fun DistrictsScreen(
    serviceSelected: String?,
    viewModel: DistrictViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val districtState by viewModel.districtState.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.app_name),
                onBackClick = { navController.popBackStack() })
        }
    ) { innerPadding ->
        DistrictScreenContent(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            innerPadding = innerPadding,
            context = context,
            districtState = districtState,
            serviceSelected = serviceSelected
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
    serviceSelected: String?
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
    ) {
        DistrictGrid(
            navController = navController,
            context = context,
            districtState = districtState,
            serviceSelected = serviceSelected
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ServiceScreenPreview() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopBar(title = "InfoPerú")
        }
    ) { innerPadding ->
        DistrictScreenContent(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            innerPadding = innerPadding,
            context = LocalContext.current,
            districtState = ResultState.Success(
                listOf(
                    District("Lima", "lima")
                )
            ),
            serviceSelected = "lima"
        )
    }
}

