package com.devpaul.infoxperu.feature.home.home_view.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalInspectionMode
import com.devpaul.infoxperu.domain.models.res.ApiException
import com.devpaul.infoxperu.domain.models.res.Gratitude
import com.devpaul.infoxperu.domain.ui.AcknowledgmentSection
import com.devpaul.infoxperu.domain.ui.SectionsRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val dollarQuoteState by viewModel.dollarQuoteState.collectAsState()
    val gratitude by viewModel.gratitude.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "LimaSegura", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Handle settings click */ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier.fillMaxSize(),
            dollarQuoteState = dollarQuoteState,
            innerPadding = innerPadding,
            gratitude = gratitude
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    dollarQuoteState: ResultState<DollarQuoteResponse>?,
    innerPadding: PaddingValues = PaddingValues(),
    gratitude: List<Gratitude> = emptyList()
) {

    var data: DollarQuoteResponse? = null
    var error: ApiException? = null

    val isPreview = LocalInspectionMode.current

    val displayGratitude = if (isPreview) {
        listOf(
            Gratitude(
                image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjmwFjRcH2AxfbYvsQt4dbqpEIvlrqQklppo179jxqtQHLeUQX6UG-ukjyQWBmvLRQsfM&usqp=CAU",
                title = "Mock Title 1"
            ),
            Gratitude(image = "https://via.placeholder.com/150", title = "Mock Title 2"),
            Gratitude(image = "https://via.placeholder.com/150", title = "Mock Title 3")
        )
    } else {
        gratitude
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        SectionHeader("Agradecimientos")
        AcknowledgmentSection(displayGratitude)
        SectionHeader("Secciones disponibles")
        SectionsRow()
        SectionHeader("Información diaria")
        // HyperlinkText(url = "https://www.deperu.com", text = "https://www.deperu.com")

        when (dollarQuoteState) {
            is ResultState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is ResultState.Success -> {
                data = dollarQuoteState.data
            }

            is ResultState.Error -> {
                error = dollarQuoteState.exception as? ApiException
            }

            else -> {
                // Mostrar algo por defecto o dejar en blanco
            }
        }

        InformationCard(
            imageRes = R.drawable.background_dolar_home,
            title = "Valor del dólar (USD)",
            buys = (data?.cotizacion?.firstOrNull()?.compra ?: "Error de servicio").toString(),
            sale = (data?.cotizacion?.firstOrNull()?.venta ?: "Error de servicio").toString(),
            place = data?.sitio ?: "Error de servicio",
            date = data?.fecha ?: "Error de servicio"
        )

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
fun HomeContentPreview() {
    InfoXPeruTheme {
        HomeContent(
            dollarQuoteState = null
        )
    }
}
