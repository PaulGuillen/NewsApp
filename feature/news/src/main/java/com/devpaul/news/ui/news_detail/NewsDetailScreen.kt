package com.devpaul.news.ui.news_detail

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.devpaul.shared.screen.goBackWithReload
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsDetailsScreen(
    navController: NavHostController,
    newsType: String?,
    country: String?,
) {
    val viewModel: NewsDetailViewModel = koinViewModel()

    LaunchedEffect(newsType, country) {
        viewModel.setNewsData(newsType, country)
    }


    BackHandler {
        goBackWithReload(navController)
    }
}
