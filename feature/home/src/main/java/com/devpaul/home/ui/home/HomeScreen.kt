package com.devpaul.home.ui.home

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.HolidayAlertEntity
import com.devpaul.home.domain.entity.PriceItemEntity
import com.devpaul.home.domain.entity.SeasonDetailEntity
import com.devpaul.home.domain.entity.SeasonEntity
import com.devpaul.home.domain.entity.SeasonYearEntity
import com.devpaul.home.domain.entity.SunatExchangeRateEntity
import com.devpaul.home.domain.entity.UITEntity
import com.devpaul.home.ui.home.components.IndicatorCard
import com.devpaul.home.ui.home.components.emergency.EmergencyBottomSheet
import com.devpaul.home.ui.home.components.emergency.EmergencyCard
import com.devpaul.home.ui.home.components.latestgeneral.LatestGeneralSection
import com.devpaul.home.ui.home.components.storysection.SeasonSection
import com.devpaul.shared.ui.components.molecules.AppHeader
import com.devpaul.shared.ui.components.molecules.HomeBottomBar
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = koinViewModel()
    val context = LocalContext.current

    BaseScreenWithState(
        viewModel = viewModel,
        onUiEvent = { event, _ ->
            when (event) {
                is HomeUiEvent.DialNumber -> {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel: ${event.number}".toUri()
                    }
                    context.startActivity(intent)
                }
            }
        },
        navController = navController,
    ) { _, uiState, onIntent, _, _ ->
        BaseContentLayout(
            isBodyScrollable = true,
            header = { HomeHeader() },
            body = { HomeBody(uiState = uiState, onIntent = onIntent) },
            footer = { HomeBottomBar(navController) },
        )
    }
}

@Composable
fun HomeHeader() {
    AppHeader(
        title = "Emergencias PE",
        subtitle = "Lunes, 24 de Mayo",
        icon = Icons.Default.Public,
        onNotificationClick = { }
    )
}

@Composable
fun HomeBody(
    uiState: HomeUiState,
    onIntent: (HomeUiIntent) -> Unit,
) {
    if (uiState.showEmergencySheet) {
        EmergencyBottomSheet(
            onDismiss = { onIntent(HomeUiIntent.HideEmergencySheet) },
            onCall = { number -> onIntent(HomeUiIntent.DialEmergency(number)) }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HomeIndicatorsSection(uiState = uiState)
        Spacer(modifier = Modifier.height(16.dp))
        EmergencyCard(onClick = { onIntent(HomeUiIntent.ShowEmergencySheet) })
        Spacer(modifier = Modifier.height(24.dp))
        SeasonSection(seasonState = uiState.season)
        Spacer(modifier = Modifier.height(24.dp))
        LatestGeneralSection(uiState = uiState)
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun HomeIndicatorsSection(uiState: HomeUiState) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        IndicatorCard(
            modifier = Modifier.weight(1f),
            title = "UIT ${uiState.uitValue.let { if (it is ResultState.Success) it.response.year ?: "" else "" }}",
            subtitle = "Valor tributario anual",
            badgeText = "INFO",
            valueContent = {
                when (val state = uiState.uitValue) {
                    is ResultState.Success -> {
                        val value = state.response.value ?: 0.0
                        val formatted =
                            NumberFormat.getNumberInstance(Locale.forLanguageTag("es-PE"))
                                .format(value.toInt())
                        Text(
                            text = "S/ $formatted",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    }

                    is ResultState.Loading -> Text(
                        text = "Cargando...",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiaryContainer
                    )

                    is ResultState.Error -> Text(
                        text = "N/A",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiaryContainer
                    )

                    else -> Text(
                        text = "-",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiaryContainer
                    )
                }
            }
        )

        IndicatorCard(
            modifier = Modifier.weight(1f),
            title = "USD / PEN",
            subtitle = "Compra / Venta Interb.",
            variationText = run {
                val state = uiState.dollarQuote
                if (state is ResultState.Success) {
                    val prices = state.response.prices
                    val buy = prices?.firstOrNull()?.buy
                    val sell = prices?.firstOrNull()?.sell
                    if (buy != null && sell != null) {
                        val diff = (sell - buy) / buy * 100.0
                        val arrow = if (diff >= 0) "↑" else "↓"
                        val perc = String.format(Locale.US, "%.2f", abs(diff))
                        "$arrow $perc%"
                    } else null
                } else null
            }
        ) {
            when (val state = uiState.dollarQuote) {
                is ResultState.Success -> {
                    val buy = state.response.prices?.firstOrNull()?.buy ?: 0.0
                    val sell = state.response.prices?.firstOrNull()?.sell ?: 0.0
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = String.format(Locale.US, "%.3f", buy),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            maxLines = 1
                        )
                        Text(
                            text = " / ",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        )
                        Text(
                            text = String.format(Locale.US, "%.3f", sell),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9CA3AF),
                            maxLines = 1
                        )
                    }
                }

                is ResultState.Loading -> Text(
                    text = "Cargando...",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )

                is ResultState.Error -> Text(
                    text = "N/A",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )

                else -> Text(
                    text = "-",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
            }
        }
    }
}

@Preview(
    name = "General Preview - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "General Preview - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeGeneralPreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        BaseContentLayout(
            isBodyScrollable = true,
            header = { HomeHeader() },
            body = {
                HomeBody(
                    uiState = HomeUiState(
                        dollarQuote = ResultState.Success(
                            response = DollarQuoteEntity(
                                service = "Tipo de cambio referencial dolares",
                                site = "DePeru.com",
                                link = "https://www.deperu.com/tipo_cambio/",
                                prices = listOf(PriceItemEntity(buy = 3.512, sell = 3.521)),
                                note = "",
                                usdToEuro = "1.1702",
                                date = "03-05-2026"
                            )
                        ),
                        uitValue = ResultState.Success(
                            response = UITEntity(
                                service = "Valor de UIT anual",
                                site = "DePeru.com",
                                link = "",
                                year = 2026,
                                value = 5350.0
                            )
                        ),
                        season = ResultState.Success(
                            response = SeasonEntity(
                                years = mapOf(
                                    "2026" to SeasonYearEntity(
                                        autumn = SeasonDetailEntity(
                                            startHour = "09:46",
                                            startDay = "20",
                                            startMonth = "3",
                                            startDate = "20-3-2026",
                                            startText = "20 de Marzo de 2026",
                                            endDate = "21-6-2026"
                                        ),
                                        winter = SeasonDetailEntity(
                                            startHour = "03:24",
                                            startDay = "21",
                                            startMonth = "6",
                                            startDate = "21-6-2026",
                                            startText = "21 de Junio de 2026",
                                            endDate = "22-9-2026"
                                        ),
                                        spring = SeasonDetailEntity(
                                            startHour = "19:05",
                                            startDay = "22",
                                            startMonth = "9",
                                            startDate = "22-9-2026",
                                            startText = "22 de Setiembre de 2026",
                                            endDate = "21-12-2026"
                                        ),
                                        summer = SeasonDetailEntity(
                                            startHour = "15:50",
                                            startDay = "21",
                                            startMonth = "12",
                                            startDate = "21-12-2026",
                                            startText = "21 de Diciembre de 2026",
                                            endDate = "20-3-2027"
                                        )
                                    )
                                )
                            )
                        ),
                        holidayAlert = ResultState.Success(
                            response = HolidayAlertEntity(
                                service = "Alerta de Feriados",
                                source = "Calendario en DePeru.com",
                                link = "https://www.deperu.com/calendario/feriados.php",
                                date = "03-05-2026",
                                alert = "No es feriado",
                                status = "0",
                                title = "",
                                scope = "",
                                data = "Existe más información regional"
                            )
                        ),
                        sunatExchangeRate = ResultState.Success(
                            response = SunatExchangeRateEntity(
                                source = "SUNAT",
                                buy = 3.512,
                                sell = 3.521,
                                currency = "USD",
                                date = "2026-05-03"
                            )
                        )
                    ),
                    onIntent = {},
                )
            },
            footer = { HomeBottomBar(rememberNavController()) }
        )
    }
}

@Preview(
    name = "Home - Loading - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Home - Loading - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeLoadingPreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        BaseContentLayout(
            isBodyScrollable = true,
            header = { HomeHeader() },
            body = {
                HomeBody(
                    uiState = HomeUiState(
                        dollarQuote = ResultState.Loading,
                        uitValue = ResultState.Loading,
                        season = ResultState.Loading,
                        holidayAlert = ResultState.Loading,
                        sunatExchangeRate = ResultState.Loading,
                        section = ResultState.Loading,
                        showEmergencySheet = false
                    ),
                    onIntent = {},
                )
            },
            footer = { HomeBottomBar(rememberNavController()) }
        )
    }
}

@Preview(
    name = "Home - Idle - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Home - Idle - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeIdlePreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        BaseContentLayout(
            isBodyScrollable = true,
            header = { HomeHeader() },
            body = {
                HomeBody(
                    uiState = HomeUiState(
                        dollarQuote = ResultState.Idle,
                        uitValue = ResultState.Idle,
                        season = ResultState.Idle,
                        holidayAlert = ResultState.Idle,
                        sunatExchangeRate = ResultState.Idle,
                        section = ResultState.Idle,
                        showEmergencySheet = false
                    ),
                    onIntent = {},
                )
            },
            footer = { HomeBottomBar(rememberNavController()) }
        )
    }
}

@Preview(
    name = "Home - Error - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Home - Error - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeErrorPreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        BaseContentLayout(
            isBodyScrollable = true,
            header = { HomeHeader() },
            body = {
                HomeBody(
                    uiState = HomeUiState(
                        dollarQuote = ResultState.Error("Error al cargar tipo de cambio"),
                        uitValue = ResultState.Error("Error al cargar UIT"),
                        season = ResultState.Error("Error al cargar estaciones"),
                        holidayAlert = ResultState.Error("Error al cargar feriados"),
                        sunatExchangeRate = ResultState.Error("Error al cargar SUNAT"),
                        section = ResultState.Error("Error"),
                        showEmergencySheet = false
                    ),
                    onIntent = {},
                )
            },
            footer = { HomeBottomBar(rememberNavController()) }
        )
    }
}

@Preview(
    name = "Home - Emergency Sheet - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Home - Emergency Sheet - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeEmergencyPreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        BaseContentLayout(
            isBodyScrollable = true,
            header = { HomeHeader() },
            body = {
                HomeBody(
                    uiState = HomeUiState(
                        dollarQuote = ResultState.Success(
                            response = DollarQuoteEntity(
                                service = "Tipo de cambio",
                                site = "Demo",
                                link = "",
                                prices = listOf(PriceItemEntity(buy = 3.5, sell = 3.51)),
                                note = "",
                                usdToEuro = "",
                                date = "03-05-2026"
                            )
                        ),
                        uitValue = ResultState.Success(
                            response = UITEntity(
                                service = "UIT",
                                site = "Demo",
                                link = "",
                                year = 2026,
                                value = 5350.0
                            )
                        ),
                        season = ResultState.Loading,
                        holidayAlert = ResultState.Success(
                            response = HolidayAlertEntity(
                                service = "Alerta",
                                source = "Demo",
                                link = "",
                                date = "03-05-2026",
                                alert = "No es feriado",
                                status = "0",
                                title = "",
                                scope = "",
                                data = ""
                            )
                        ),
                        sunatExchangeRate = ResultState.Success(
                            response = SunatExchangeRateEntity(
                                source = "SUNAT",
                                buy = 3.5,
                                sell = 3.51,
                                currency = "USD",
                                date = "2026-05-03"
                            )
                        ),
                        section = ResultState.Loading,
                        showEmergencySheet = true
                    ),
                    onIntent = {},
                )
            },
            footer = { HomeBottomBar(rememberNavController()) }
        )
    }
}