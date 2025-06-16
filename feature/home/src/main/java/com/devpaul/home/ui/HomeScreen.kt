package com.devpaul.home.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.data.datasource.mock.DollarQuoteMock
import com.devpaul.home.data.datasource.mock.GratitudeMock
import com.devpaul.home.data.datasource.mock.SectionMock
import com.devpaul.home.data.datasource.mock.UITMock
import com.devpaul.home.ui.components.AcknowledgmentSection
import com.devpaul.home.ui.components.DollarQuoteCard
import com.devpaul.home.ui.components.SectionsRow
import com.devpaul.home.ui.components.UITCard
import com.devpaul.shared.screen.BaseScreenWithState
import com.devpaul.shared.ui.extension.BottomNavigationBar
import com.devpaul.shared.ui.extension.SectionHeader
import com.devpaul.shared.ui.extension.TopBar
import com.devpaul.shared.ui.extension.handleDefaultErrors
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = koinViewModel()

    val context = LocalContext.current

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
        onUiEvent = { event, _ ->
            when (event) {
                is HomeUiEvent.DollarQuoteSuccess -> {
                   viewModel.openDollarQuote(event.response)
                }

                is HomeUiEvent.UITSuccess -> {
                    viewModel.openUIT(event.response)
                }

                is HomeUiEvent.GratitudeSuccess -> {
                    viewModel.openGratitude(event.response)
                }

                is HomeUiEvent.SectionSuccess -> {
                    viewModel.openSection(event.response)
                }

                is HomeUiEvent.DollarQuoteError -> {
                    viewModel.handleErrorEvents(type = "dollarQuote", error = event.error)
                }

                is HomeUiEvent.UITError -> {
                    viewModel.handleErrorEvents(type = "uit", error= event.error)
                }

                is HomeUiEvent.GratitudeError -> {
                    viewModel.handleErrorEvents(type = "gratitude", error= event.error)
                }

                is HomeUiEvent.SectionError -> {
                    viewModel.handleErrorEvents(type = "section", error = event.error)
                }
            }
        },
        onDefaultError = { error, showSnackBar ->
            handleDefaultErrors(error, showSnackBar)
        }
    ) { _, uiState, _, _, _ ->
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
            HomeContent(
                modifier = Modifier.fillMaxSize(),
                innerPadding = innerPadding,
                uiState = uiState,
                context = context
            )
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    innerPadding: PaddingValues,
    context: Context
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        SectionHeader(stringResource(R.string.section_gratitude_header))
        AcknowledgmentSection(
            context = context,
            gratitudeState = uiState.gratitude,
        )

        SectionHeader(stringResource(R.string.section_available_sections_header))
        SectionsRow(
            context = context,
            sectionState = uiState.section,
        )

        SectionHeader(stringResource(R.string.section_daily_info_header))
        DollarQuoteCard(
            context = context,
            dollarQuoteState = uiState.dollarQuote,
        )

        UITCard(
            context = context,
            uitState = uiState.uitValue
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopBar(title = "InfoPerú")
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier.fillMaxSize(),
            uiState = HomeUiState(
                dollarQuote = ResultState.Success(DollarQuoteMock().dollarQuoteMock),
                uitValue = ResultState.Success(UITMock().uitMock),
                gratitude = ResultState.Success(GratitudeMock().gratitudeMock),
                section = ResultState.Success(SectionMock().sectionMock),
            ),
            innerPadding = innerPadding,
            context = LocalContext.current,
        )
    }
}