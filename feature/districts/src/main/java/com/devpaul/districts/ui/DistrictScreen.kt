package com.devpaul.districts.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.devpaul.core_platform.R
import com.devpaul.shared.ui.components.molecules.BottomNavigationBar
import com.devpaul.shared.ui.components.molecules.TopBar

@Composable
fun DistrictScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.header_available_zones),
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Text(
            text = "District New Screen",
            modifier = Modifier.padding(innerPadding)
        )
    }
}