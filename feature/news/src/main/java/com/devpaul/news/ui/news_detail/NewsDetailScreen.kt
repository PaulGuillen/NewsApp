package com.devpaul.news.ui.news_detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsDetailsScreen(
    navController: NavHostController,
    newsType: String?,
    country: String?,
) {
    val viewModel: NewsDetailViewModel = koinViewModel()
    viewModel.setNewsData(newsType, country)

}
