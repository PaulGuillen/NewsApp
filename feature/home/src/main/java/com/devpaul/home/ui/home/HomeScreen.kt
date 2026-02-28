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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.home.ui.home.components.IndicatorCard
import com.devpaul.home.ui.home.components.emergency.EmergencyBottomSheet
import com.devpaul.home.ui.home.components.emergency.EmergencyCard
import com.devpaul.home.ui.home.components.latestnew.NewsItemPro
import com.devpaul.home.ui.home.components.storysection.StoriesSection
import com.devpaul.shared.ui.components.molecules.AppHeader
import com.devpaul.shared.ui.components.molecules.HomeBottomBar
import com.devpaul.shared.ui.components.molecules.PrimaryOutlinedButton
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

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
                        data = "tel:${event.number}".toUri()
                    }
                    context.startActivity(intent)
                }
            }
        },
        navController = navController,
    ) { _, uiState, onIntent, _, _ ->
        BaseContentLayout(
            isBodyScrollable = true,
            header = {
                HomeHeader()
            },
            body = {
                HomeBody(
                    uiState = uiState,
                    onIntent = onIntent,
                    navController = navController
                )
            },
            footer = {
                HomeBottomBar(navController)
            },
        )
    }
}

@Composable
fun HomeHeader() {
    AppHeader(
        title = "Finanzas PE",
        subtitle = "Lunes, 24 de Mayo",
        icon = Icons.Default.Public,
        onNotificationClick = { }
    )
}

@Composable
fun HomeBody(
    uiState: HomeUiState,
    onIntent: (HomeUiIntent) -> Unit,
    navController: NavHostController
) {

    if (uiState.showEmergencySheet) {
        EmergencyBottomSheet(
            onDismiss = {
                onIntent(HomeUiIntent.HideEmergencySheet)
            },
            onCall = { number ->
                onIntent(HomeUiIntent.DialEmergency(number))
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        HomeIndicatorsSection()

        Spacer(modifier = Modifier.height(16.dp))

        EmergencyCard(
            onClick = {
                onIntent(HomeUiIntent.ShowEmergencySheet)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        StoriesSection()

        Spacer(modifier = Modifier.height(24.dp))

        LatestNewsSection(
            onClickMore = {
                navController.navigate("allNews")
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun HomeIndicatorsSection() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        IndicatorCard(
            modifier = Modifier.weight(1f),
            title = "UIT 2024",
            subtitle = "Valor tributario anual",
            badgeText = "INFO",
            valueContent = {
                Text(
                    text = "S/ 5,150",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
            }
        )

        IndicatorCard(
            modifier = Modifier.weight(1f),
            title = "USD / PEN",
            subtitle = "Compra / Venta Interb.",
            variationText = "↑ 0.2%",
            valueContent = {
                Text(
                    buildAnnotatedString {
                        append("3.72")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF9CA3AF)
                            )
                        ) {
                            append(" / 3.75")
                        }
                    },
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
            }
        )
    }
}

@Composable
fun LatestNewsSection(
    onClickMore: () -> Unit
) {
    Column {
        Text(
            "Últimas Noticias",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiaryContainer,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        repeat(3) {
            NewsItemPro()
        }

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryOutlinedButton(
            text = "Cargar más noticias",
            onClick = onClickMore
        )
    }
}

@Preview(
    name = "Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeBodyPreview() {
    InfoXPeruTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        BaseContentLayout(
            isBodyScrollable = true,
            header = {
                HomeHeader()
            },
            body = {
                HomeBody(
                    uiState = HomeUiState(),
                    onIntent = {},
                    navController = rememberNavController(),
                )
            },
            footer = {
                HomeBottomBar(rememberNavController())
            },
        )
    }
}