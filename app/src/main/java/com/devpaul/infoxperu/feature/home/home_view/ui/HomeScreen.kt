package com.devpaul.infoxperu.feature.home.home_view.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse
import com.devpaul.infoxperu.domain.ui.BottomNavigationBar
import com.devpaul.infoxperu.domain.ui.InformationCard
import com.devpaul.infoxperu.domain.ui.SectionHeader
import com.devpaul.infoxperu.domain.ui.UITCard
import com.devpaul.infoxperu.ui.theme.InfoXPeruTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.domain.models.res.ApiException
import com.devpaul.infoxperu.domain.models.res.Gratitude
import com.devpaul.infoxperu.domain.models.res.SectionItem
import com.devpaul.infoxperu.domain.ui.AcknowledgmentSection
import com.devpaul.infoxperu.domain.ui.SectionsRow
import com.devpaul.infoxperu.domain.ui.TopBar

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val dollarQuoteState by viewModel.dollarQuoteState.collectAsState()
    val gratitude by viewModel.gratitude.collectAsState()
    val sectionItems by viewModel.sections.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(title = "InfoPerú")
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier.fillMaxSize(),
            dollarQuoteState = dollarQuoteState,
            innerPadding = innerPadding,
            gratitude = gratitude,
            sectionItems = sectionItems,
            context = context
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    dollarQuoteState: ResultState<DollarQuoteResponse>?,
    innerPadding: PaddingValues = PaddingValues(),
    gratitude: List<Gratitude> = emptyList(),
    sectionItems: List<SectionItem> = emptyList(),
    context: Context
) {

    var data: DollarQuoteResponse? = null
    var error: ApiException? = null

    val isPreview = LocalInspectionMode.current

    val displayGratitude = if (isPreview) {
        listOf(
            Gratitude(image = "https://via.placeholder.com/150", title = "Mock Title 2"),
            Gratitude(image = "https://via.placeholder.com/150", title = "Mock Title 3")
        )
    } else {
        gratitude
    }

    val displaySections = if (isPreview) {
        listOf(
            SectionItem(title = "Noticias", type = "news"),
            SectionItem(title = "Distritos", type = "districts")
        )
    } else {
        sectionItems
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        SectionHeader("Agradecimientos")
        AcknowledgmentSection(displayGratitude, context)
        SectionHeader("Secciones disponibles")
        SectionsRow(displaySections, context)
        SectionHeader("Información diaria")
        InformationCard(dollarQuoteState = dollarQuoteState)
        UITCard(
            imageRes = R.drawable.ic_launcher_background,
            title = "Valor de UIT",
            uit = 5150.0,
            periodo = 2024,
            fuente = "DePeru.com"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    InfoXPeruTheme {
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
                dollarQuoteState = null,
                innerPadding = innerPadding,
                gratitude = emptyList(),
                context = LocalContext.current
            )
        }
    }
}
