package com.devpaul.home.ui.home

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.devpaul.shared.ui.components.atoms.base.SectionHeader
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
            floatingActionButton = {
                HomeFab()
            }
        )
    }
}

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Finanzas PE",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Lunes, 24 de Mayo",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        IconButton(onClick = {}) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
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

        FeaturedStoriesSection()

        Spacer(modifier = Modifier.height(24.dp))

        LatestNewsSection(
            onClickMore = {
                navController.navigate("allNews")
            }
        )

        Spacer(modifier = Modifier.height(80.dp))
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
                    color = MaterialTheme.colorScheme.onPrimaryContainer
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
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        )
    }
}

@Composable
fun FeaturedStoriesSection() {

    Column {
        SectionHeader("Historias Destacadas")

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(5) {
                FeaturedCard()
            }
        }
    }
}

@Composable
fun FeaturedCard() {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(180.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "Impacto del nuevo puerto de Chancay en el PBI",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LatestNewsSection(onClickMore: () -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Últimas Noticias",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            TextButton(onClick = onClickMore) {
                Text("Ver todas")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        repeat(3) {
            NewsItemCard()
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickMore
        ) {
            Text("Cargar más noticias")
        }
    }
}

@Composable
fun NewsItemCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(12.dp)
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    "Bolsa de Valores de Lima cierra al alza...",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "15 min",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun HomeBottomBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.ShowChart, null) },
            label = { Text("Mercados") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.AccountBalanceWallet, null) },
            label = { Text("Cartera") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Settings, null) },
            label = { Text("Ajustes") }
        )
    }
}

@Composable
fun HomeFab() {
    FloatingActionButton(
        onClick = {},
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(Icons.Default.CalendarMonth, contentDescription = null)
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
        HomeBody(
            uiState = HomeUiState(),
            onIntent = {},
            navController = rememberNavController()
        )
    }
}