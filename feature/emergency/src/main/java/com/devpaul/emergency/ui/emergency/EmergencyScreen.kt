package com.devpaul.emergency.ui.emergency

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.devpaul.core_platform.R
import com.devpaul.shared.ui.components.molecules.BottomNavigationBar
import com.devpaul.shared.ui.components.molecules.TopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun EmergencyScreen(
    navHostController: NavHostController
) {

    val viewModel: EmergencyViewModel = koinViewModel()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.header_available_zones),
            )
        },
        bottomBar = {
            BottomNavigationBar(navHostController)
        }
    ) { innerPadding ->
        Text(
            text = "District New Screen",
            modifier = Modifier.padding(innerPadding)
        )
    }
}