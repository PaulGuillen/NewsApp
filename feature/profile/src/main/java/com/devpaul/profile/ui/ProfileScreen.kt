package com.devpaul.profile.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.devpaul.core_platform.R
import com.devpaul.shared.ui.extension.BottomNavigationBar
import com.devpaul.shared.ui.extension.TopBar

@Composable
fun ProfileScreen(
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.app_name),
                onLogoutClick = {
                    // viewModel.logOut(navController)
                })
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Text(
            text = "Profile Screen",
            modifier = Modifier.padding(innerPadding)
        )
    }
}