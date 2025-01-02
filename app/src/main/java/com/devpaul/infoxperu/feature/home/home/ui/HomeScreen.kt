package com.devpaul.infoxperu.feature.home.home.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.mocks.DollarQuoteMock
import com.devpaul.infoxperu.core.mocks.GratitudeMock
import com.devpaul.infoxperu.core.mocks.SectionMock
import com.devpaul.infoxperu.core.mocks.UITMock
import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse
import com.devpaul.infoxperu.domain.models.res.Gratitude
import com.devpaul.infoxperu.domain.models.res.SectionItem
import com.devpaul.infoxperu.domain.models.res.UITResponse
import com.devpaul.infoxperu.domain.screen.BaseScreen
import com.devpaul.infoxperu.domain.ui.home_screen.AcknowledgmentSection
import com.devpaul.infoxperu.domain.ui.home_screen.InformationCard
import com.devpaul.infoxperu.domain.ui.home_screen.SectionsRow
import com.devpaul.infoxperu.domain.ui.home_screen.UITCard
import com.devpaul.infoxperu.domain.ui.utils.BottomNavigationBar
import com.devpaul.infoxperu.domain.ui.utils.SectionHeader
import com.devpaul.infoxperu.domain.ui.utils.TopBar

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val dollarQuoteState by viewModel.dollarQuoteState.collectAsState()
    val uitState by viewModel.uitState.collectAsState()
    val gratitudeState by viewModel.gratitudeState.collectAsState()
    val sectionItemsState by viewModel.sectionsState.collectAsState()
    val context = LocalContext.current

    BaseScreen { _, _ ->
        LaunchedEffect(Unit) {
            viewModel.executeUiIntent(HomeUiIntent.DollarQuote)
            viewModel.executeUiIntent(HomeUiIntent.UIT)
            viewModel.executeUiIntent(HomeUiIntent.Gratitude)
            viewModel.executeUiIntent(HomeUiIntent.Sections)
        }

        Scaffold(
            topBar = {
                TopBar(title = stringResource(R.string.app_name),
                    onLogoutClick = {
                        viewModel.logOut(navController)
                    })
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { innerPadding ->
            HomeContent(
                modifier = Modifier.fillMaxSize(),
                dollarQuoteState = dollarQuoteState,
                uitState = uitState,
                innerPadding = innerPadding,
                gratitudeState = gratitudeState,
                sectionItemsState = sectionItemsState,
                context = context
            )
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    dollarQuoteState: ResultState<DollarQuoteResponse>?,
    uitState: ResultState<UITResponse>?,
    innerPadding: PaddingValues = PaddingValues(),
    gratitudeState: ResultState<List<Gratitude>>,
    sectionItemsState: ResultState<List<SectionItem>>,
    context: Context
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        SectionHeader(stringResource(R.string.section_gratitude_header))
        AcknowledgmentSection(gratitudeState = gratitudeState, context = context)

        SectionHeader(stringResource(R.string.section_available_sections_header))
        SectionsRow(sectionItemsState = sectionItemsState, context = context)

        SectionHeader(stringResource(R.string.section_daily_info_header))
        InformationCard(dollarQuoteState = dollarQuoteState, context = context)

        UITCard(uitState = uitState, context = context)
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
            dollarQuoteState = ResultState.Success(DollarQuoteMock().dollarQuoteMock),
            uitState = ResultState.Success(UITMock().uitMock),
            innerPadding = innerPadding,
            gratitudeState = ResultState.Success(GratitudeMock().gratitudeMock),
            sectionItemsState = ResultState.Success(SectionMock().sectionMock),
            context = LocalContext.current,
        )
    }
}
