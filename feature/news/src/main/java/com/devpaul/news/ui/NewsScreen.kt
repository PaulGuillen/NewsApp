package com.devpaul.news.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devpaul.core_platform.R
import com.devpaul.shared.extension.handleDefaultErrors
import com.devpaul.shared.screen.BaseScreenWithState
import com.devpaul.shared.ui.extension.BottomNavigationBar
import com.devpaul.shared.ui.extension.TopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(navController: NavHostController) {

    val viewModel: NewsViewModel = koinViewModel()
    val context = LocalContext.current
    val uiModel = remember { mutableStateOf(NewsUiModel()) }

    BaseScreenWithState(
        viewModel = viewModel,
        onUiEvent = { event, _ ->
            when (event) {
                is NewsUiEvent.CountrySuccess -> {
                    uiModel.value = uiModel.value.copy(country = event.response)
                }
                is NewsUiEvent.CountryError -> {
                    uiModel.value.copy(countryError = event.error.apiErrorResponse?.message)
                }
            }
        },
        onDefaultError = { error, showSnackBar ->
            handleDefaultErrors(error, showSnackBar)
        }
    ) { _, uiState, callbacks, _ ->
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.app_name),
                    onLogoutClick = {
                        //   viewModel.logOut(navController)
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { innerPadding ->
            NewsContent(
                context = context,
                navController = navController,
                modifier = Modifier.fillMaxSize(),
                innerPadding = innerPadding,
                uiState = uiState,
                uiModel = uiModel.value,
            )
        }
    }
}

@Composable
fun NewsContent(
    context: Context,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    uiState: NewsUiState,
    uiModel: NewsUiModel,
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.padding(top = 16.dp))

    }
}