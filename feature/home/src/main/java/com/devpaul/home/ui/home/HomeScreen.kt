package com.devpaul.home.ui.home

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
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.entity.UITEntity
import com.devpaul.home.ui.home.components.AcknowledgmentSection
import com.devpaul.home.ui.home.components.InformationCard
import com.devpaul.home.ui.home.components.SectionsRow
import com.devpaul.home.ui.home.components.UITCard
import com.devpaul.shared.extension.handleDefaultErrors
import com.devpaul.shared.screen.BaseScreenWithState
import com.devpaul.shared.ui.extension.BottomNavigationBar
import com.devpaul.shared.ui.extension.SectionHeader
import com.devpaul.shared.ui.extension.TopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = koinViewModel()

    val context = LocalContext.current
    val dollarQuoteState = remember { mutableStateOf<DollarQuoteEntity?>(null) }
    val uitState = remember { mutableStateOf<UITEntity?>(null) }
    val gratitudeState = remember { mutableStateOf<GratitudeEntity?>(null) }
    val sectionItemsState = remember { mutableStateOf<SectionEntity?>(null) }
    val dollarQuoteErrorState = remember { mutableStateOf<String?>(null) }
    val uitErrorState = remember { mutableStateOf<String?>(null) }
    val gratitudeErrorState = remember { mutableStateOf<String?>(null) }
    val sectionItemsErrorState = remember { mutableStateOf<String?>(null) }

    BaseScreenWithState(
        viewModel = viewModel,
        onInit = {
            HomeUiIntent.DollarQuote
            HomeUiIntent.UIT
            HomeUiIntent.Gratitude
            HomeUiIntent.Sections
        },
        onUiEvent = { event, _ ->
            when (event) {
                is HomeUiEvent.DollarQuoteSuccess -> {
                    dollarQuoteState.value = event.response
                }

                is HomeUiEvent.UITSuccess -> {
                    uitState.value = event.response
                }

                is HomeUiEvent.GratitudeSuccess -> {
                    gratitudeState.value = event.response
                }

                is HomeUiEvent.SectionSuccess -> {
                    sectionItemsState.value = event.response
                }

                is HomeUiEvent.DollarQuoteError -> {
                    dollarQuoteErrorState.value = event.error.apiErrorResponse?.message
                }

                is HomeUiEvent.UITError -> {
                    uitErrorState.value = event.error.apiErrorResponse?.message
                }

                is HomeUiEvent.GratitudeError -> {
                    gratitudeErrorState.value = event.error.apiErrorResponse?.message
                }

                is HomeUiEvent.SectionError -> {
                    sectionItemsErrorState.value = event.error.apiErrorResponse?.message
                }
            }
        },
        onDefaultError = { error, showSnackBar ->
            handleDefaultErrors(error, showSnackBar)
        }
    ) { _, uiState, _, _ ->
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
                dollarQuote = dollarQuoteState.value,
                dollarQuoteError = dollarQuoteErrorState.value,
                dollarQuoteLoading = uiState.isDollarQuoteLoading,
                uit = uitState.value,
                uitError = uitErrorState.value,
                uitLoading = uiState.isUITLoading,
                gratitude = gratitudeState.value,
                gratitudeError = gratitudeErrorState.value,
                gratitudeLoading = uiState.isGratitudeLoading,
                section = sectionItemsState.value,
                sectionError = sectionItemsErrorState.value,
                sectionLoading = uiState.isSectionsLoading,
                innerPadding = innerPadding,
                context = context,
            )
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    dollarQuote: DollarQuoteEntity?,
    dollarQuoteError: String?,
    dollarQuoteLoading: Boolean,
    uit: UITEntity?,
    uitError: String?,
    uitLoading: Boolean,
    gratitude: GratitudeEntity?,
    gratitudeError: String?,
    gratitudeLoading: Boolean,
    section: SectionEntity?,
    sectionError: String?,
    sectionLoading: Boolean,
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
            gratitude = gratitude,
            gratitudeError = gratitudeError,
            gratitudeLoading = gratitudeLoading,
        )

        SectionHeader(stringResource(R.string.section_available_sections_header))
        SectionsRow(
            context = context,
            section = section,
            sectionError = sectionError,
            sectionLoading = sectionLoading,
        )

        SectionHeader(stringResource(R.string.section_daily_info_header))

        InformationCard(
            context = context,
            dollarQuote = dollarQuote,
            dollarQuoteError = dollarQuoteError,
            dollarQuoteLoading = dollarQuoteLoading,
        )

        UITCard(
            context = context,
            uit = uit,
            uitError = uitError,
            uitLoading = uitLoading,
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
            dollarQuote = DollarQuoteMock().dollarQuoteMock,
            dollarQuoteError = null,
            dollarQuoteLoading = false,
            uit = UITMock().uitMock,
            uitError = null,
            uitLoading = false,
            gratitude = GratitudeMock().gratitudeMock,
            gratitudeError = null,
            gratitudeLoading = false,
            section = SectionMock().sectionMock,
            sectionError = null,
            sectionLoading = false,
            innerPadding = innerPadding,
            context = LocalContext.current,
        )
    }
}
