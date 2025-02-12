package com.devpaul.infoxperu.feature.home.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.domain.ui.utils.BottomNavigationBar
import com.devpaul.infoxperu.domain.ui.utils.TopBar

@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(title = "InfoPerú")
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_self_improvement_24), // Replace with actual profile image
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 8.dp)
                )
                Text(text = "David Jerome", style = MaterialTheme.typography.bodyLarge) // Use h5 or body1
                Text(text = "jerome.david@gmail.com", style = MaterialTheme.typography.bodyLarge)
            }

            // Settings Section
            Text(
                text = "Settings",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            SettingItem(label = "Phone number", action = "Add number")
            SettingItem(label = "Language", action = "English (eng)")
            SettingItem(label = "Currency", action = "US Dollar ($)")
            SettingItem(label = "Distance units", action = "Mile")
            SettingItem(label = "Country / region", action = "Canada")
            SettingItem(label = "Notifications", action = "")
        }
    }
}

@Composable
fun SettingItem(label: String, action: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = action,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            modifier = Modifier.padding(top = 4.dp)
        )
        Divider(modifier = Modifier.padding(top = 8.dp), color = Color.LightGray)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}