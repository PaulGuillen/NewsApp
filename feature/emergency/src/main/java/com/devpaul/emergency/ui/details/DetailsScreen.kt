package com.devpaul.emergency.ui.details

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.devpaul.core_platform.extension.ResultState
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
            body = {
                DetailBody(
                    uiState = uiState,
                    onIntent = onIntent,
                )
            }
        )
    }
}

@Composable
fun DetailBody(
    uiState: DetailsUiState,
    onIntent: (DetailsUiIntent) -> Unit
) {
    if (uiState.generalCase) {
        GeneralCase(uiState = uiState)
    } else if (uiState.civilCase) {
        CivilDefenseCase(uiState = uiState)
    } else if (uiState.securityCase) {
        SecurityCase(uiState = uiState)
    } else {
        // Display other case details
    }
}

@Composable
fun GeneralCase(
    uiState: DetailsUiState,
) {
    when (val state = uiState.general) {
        is ResultState.Loading -> {
            // Show loading state
        }

        is ResultState.Success -> {
            // Display general case details
        }

        is ResultState.Error -> {
            // Show error message
        }

        else -> Unit
    }
}

@Composable
fun CivilDefenseCase(
    uiState: DetailsUiState,
) {
    when (val state = uiState.civilDefense) {
        is ResultState.Loading -> {
            // Show loading state
        }

        is ResultState.Success -> {
            // Display civil defense case details
        }

        is ResultState.Error -> {
            // Show error message
        }

        else -> Unit
    }
}

@Composable
fun SecurityCase(
    uiState: DetailsUiState,
) {
    when (val state = uiState.limaSecurity) {
        is ResultState.Loading -> {
            // Show loading state
        }

        is ResultState.Success -> {
            // Display security case details
        }

        is ResultState.Error -> {
            // Show error message
        }

        else -> Unit
    }
}