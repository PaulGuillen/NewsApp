package com.example.mylist.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.shared.ui.components.molecules.AppHeader
import com.devpaul.shared.ui.components.molecules.HomeBottomBar
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import com.example.mylist.ui.components.NewsSavedCard
import com.example.mylist.ui.components.TabItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyListScreen(
    navHostController: NavHostController
) {
    val viewModel: MyListViewModel = koinViewModel()

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navHostController,
    ) { _, uiState, onIntent, _, _ ->
        BaseContentLayout(
            isBodyScrollable = true,
            header = {
                MyListHeader()
            },
            body = {
                MyListContent()
            },
            footer = {
                HomeBottomBar(navHostController)
            },
        )
    }
}

@Composable
fun MyListHeader() {
    AppHeader(
        title = "Emergencias PE",
        subtitle = "Lunes, 24 de Mayo",
        icon = Icons.Default.Public,
        onNotificationClick = { }
    )
}

@Composable
fun MyListContent() {

    val isDark = isSystemInDarkTheme()

    val background = if (isDark) Color(0xFF000000) else Color(0xFFFCFCFC)
    if (isDark) Color.White else Color.Black
    if (isDark) Color(0xFF94A3B8) else Color(0xFF6B7280)
    val divider = if (isDark) Color(0xFF1E293B) else Color(0xFFE5E7EB)

    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            TabItem("Por leer", selectedTab == 0) { selectedTab = 0 }
            Spacer(modifier = Modifier.width(8.dp))
            TabItem("Leídas", selectedTab == 1) { selectedTab = 1 }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(Modifier, DividerDefaults.Thickness, color = divider)

        Spacer(modifier = Modifier.height(16.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

            repeat(5) {
                NewsSavedCard(
                    title = "Congreso debate nueva ley de reforma constitucional sobre bicameralidad inmediata.",
                    category = "POLÍTICA • RPP",
                    time = "AYER, 08:20 PM",
                    isHighlighted = it == 1
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(
    name = "Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MyListPreview() {
    InfoXPeruTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        BaseContentLayout(
            isBodyScrollable = true,
            header = {
                MyListHeader()
            },
            body = {
                MyListContent()
            },
            footer = {
                HomeBottomBar(rememberNavController())
            },
        )
    }
}
