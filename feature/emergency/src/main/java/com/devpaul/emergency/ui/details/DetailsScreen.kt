package com.devpaul.emergency.ui.details

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    navController: NavHostController,
    type: String?,
) {

    val viewModel: DetailsViewModel = koinViewModel()

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
        onInit = { _, _ ->
            viewModel.getTypeService(type = type)
        },
    ) { _, uiState, onIntent, _, _ ->
        BaseContentLayout(
            body = {}
        )
    }

}