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
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.domain.models.res.CotizacionItem
import com.devpaul.infoxperu.domain.models.res.Gratitude
import com.devpaul.infoxperu.domain.models.res.SectionItem
import com.devpaul.infoxperu.domain.models.res.UITResponse
import com.devpaul.infoxperu.domain.ui.AcknowledgmentSection
import com.devpaul.infoxperu.domain.ui.SectionsRow
import com.devpaul.infoxperu.domain.ui.TopBar

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val dollarQuoteState by viewModel.dollarQuoteState.collectAsState()
    val uitState by viewModel.uitState.collectAsState()
    val gratitudeState by viewModel.gratitudeState.collectAsState()
    val sectionItemsState by viewModel.sectionsState.collectAsState()
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
            uitState = uitState,
            innerPadding = innerPadding,
            gratitudeState = gratitudeState,
            sectionItemsState = sectionItemsState,
            context = context
        )
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
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        SectionHeader("Agradecimientos")
        AcknowledgmentSection(gratitudeState = gratitudeState, context = context)

        SectionHeader("Secciones disponibles")
        SectionsRow(sectionItemsState = sectionItemsState, context = context)

        SectionHeader("Información diaria")
        InformationCard(dollarQuoteState = dollarQuoteState, context = context)

        UITCard(uitState = uitState, context = context)
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
                dollarQuoteState = ResultState.Success(
                    DollarQuoteResponse(
                        cotizacion = listOf(
                            CotizacionItem(
                                compra = 3.61,
                                venta = 3.72
                            )
                        ),
                        fecha = "2021-10-10",
                    )
                ),
                uitState = ResultState.Success(
                    UITResponse(
                        UIT = 123.45,
                        periodo = 1,
                        servicio = "Mock Service"
                    )
                ),
                innerPadding = innerPadding,
                gratitudeState = ResultState.Success(
                    listOf(
                        Gratitude(
                            image = "https://via.placeholder.com/150",
                            title = "Mock Title 1",
                            url = "https://example.com"
                        ),
                        Gratitude(
                            image = "https://via.placeholder.com/150",
                            title = "Mock Title 2",
                            url = "https://example.com"
                        )
                    )
                ),
                sectionItemsState = ResultState.Success(
                    listOf(
                        SectionItem(title = "Noticias", type = "news"),
                        SectionItem(title = "Distritos", type = "districts")
                    )
                ),
                context = LocalContext.current
            )
        }
    }
}
