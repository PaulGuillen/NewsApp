package com.devpaul.news.ui.news_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.devpaul.shared.screen.BaseScreenWithState
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

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
        onUiEvent = { event, _ ->
            when (event) {
                is NewsDetailUiEvent.DeltaProjectError -> {}
                is NewsDetailUiEvent.DeltaProjectSuccess -> {}
                is NewsDetailUiEvent.GoogleError -> {}
                is NewsDetailUiEvent.GoogleSuccess -> {}
                is NewsDetailUiEvent.RedditError -> {}
                is NewsDetailUiEvent.RedditSuccess -> {}
            }
        },
        onBackPressed = { onBackWithResult ->
            onBackWithResult("shouldReload", true)
        }
    ) { _, _, _, _, _ ->

    }
}
