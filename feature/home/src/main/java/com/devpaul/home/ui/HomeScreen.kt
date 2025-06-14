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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.R
import com.devpaul.home.data.datasource.mock.DollarQuoteMock
import com.devpaul.home.data.datasource.mock.GratitudeMock
import com.devpaul.home.data.datasource.mock.SectionMock
import com.devpaul.home.data.datasource.mock.UITMock
import com.devpaul.home.ui.components.AcknowledgmentSection
import com.devpaul.home.ui.components.DollarQuoteCard
import com.devpaul.home.ui.components.SectionsRow
import com.devpaul.home.ui.components.UITCard
import com.devpaul.shared.ui.extension.handleDefaultErrors
import com.devpaul.shared.screen.BaseScreenWithState
import com.devpaul.shared.ui.extension.BottomNavigationBar
import com.devpaul.shared.ui.extension.SectionHeader
import com.devpaul.shared.ui.extension.TopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = koinViewModel()

    val context = LocalContext.current
    val uiModel = remember { mutableStateOf(HomeUiModel()) }

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
        onUiEvent = { event, _ ->
            uiModel.value = when (event) {
                is HomeUiEvent.DollarQuoteSuccess -> uiModel.value.copy(dollarQuote = event.response)
                is HomeUiEvent.UITSuccess -> uiModel.value.copy(uit = event.response)
                is HomeUiEvent.GratitudeSuccess -> uiModel.value.copy(gratitude = event.response)
                is HomeUiEvent.SectionSuccess -> uiModel.value.copy(section = event.response)
                is HomeUiEvent.DollarQuoteError -> uiModel.value.copy(dollarQuoteError = event.error.apiErrorResponse?.message)
                is HomeUiEvent.UITError -> uiModel.value.copy(uitError = event.error.apiErrorResponse?.message)
                is HomeUiEvent.GratitudeError -> uiModel.value.copy(gratitudeError = event.error.apiErrorResponse?.message)
                is HomeUiEvent.SectionError -> uiModel.value.copy(sectionError = event.error.apiErrorResponse?.message)
            }
        },
        onDefaultError = { error, showSnackBar ->
            handleDefaultErrors(error, showSnackBar)
        }
    ) { _, uiState, _, _,_ ->
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
                uiModel = uiModel.value,
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
    uiModel: HomeUiModel,
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
            gratitude = uiModel.gratitude,
            gratitudeError = uiModel.gratitudeError,
            gratitudeLoading = uiState.isGratitudeLoading,
        )

        SectionHeader(stringResource(R.string.section_available_sections_header))
        SectionsRow(
            context = context,
            section = uiModel.section,
            sectionError = uiModel.sectionError,
            sectionLoading = uiState.isSectionsLoading,
        )

        SectionHeader(stringResource(R.string.section_daily_info_header))
        DollarQuoteCard(
            context = context,
            dollarQuote = uiModel.dollarQuote,
            dollarQuoteError = uiModel.dollarQuoteError,
            dollarQuoteLoading = uiState.isDollarQuoteLoading,
        )

        UITCard(
            context = context,
            uit = uiModel.uit,
            uitError = uiModel.uitError,
            uitLoading = uiState.isUITLoading,
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
            uiModel = HomeUiModel(
                dollarQuote = DollarQuoteMock().dollarQuoteMock,
                uit = UITMock().uitMock,
                gratitude = GratitudeMock().gratitudeMock,
                section = SectionMock().sectionMock,
            ),
            uiState = HomeUiState(
                isDollarQuoteLoading = false,
                isUITLoading = false,
                isGratitudeLoading = false,
                isSectionsLoading = false
            ),
            innerPadding = innerPadding,
            context = LocalContext.current,
        )
    }
}