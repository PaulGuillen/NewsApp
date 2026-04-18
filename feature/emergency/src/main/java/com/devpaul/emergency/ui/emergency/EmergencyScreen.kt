package com.devpaul.emergency.ui.emergency

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.emergency.domain.entity.SectionEntity
import com.devpaul.emergency.domain.entity.SectionItemEntity
import com.devpaul.emergency.ui.emergency.components.CriticalServiceItem
import com.devpaul.emergency.ui.emergency.components.EmergencySearchBar
import com.devpaul.emergency.ui.emergency.components.SmallServiceCard
import com.devpaul.shared.ui.components.molecules.AppHeader
import com.devpaul.shared.ui.components.molecules.HomeBottomBar
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun EmergencyScreen(
    navHostController: NavHostController
) {

    val viewModel: EmergencyViewModel = koinViewModel()

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navHostController,
    ) { _, uiState, onIntent, _, _ ->
        BaseContentLayout(
            isBodyScrollable = true,
            header = {
                EmergencyHeader()
            },
            body = {
                EmergencyBody(
                    uiState = uiState,
                    onIntent = onIntent,
                )
            },
            footer = {
                HomeBottomBar(navHostController)
            },
        )
    }
}

@Composable
fun EmergencyHeader() {
    AppHeader(
        title = "Emergencias PE",
        subtitle = "Lunes, 24 de Mayo",
        icon = Icons.Default.Public,
        onNotificationClick = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyBody(
    uiState: EmergencyUiState,
    onIntent: (EmergencyUiIntent) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        EmergencySearchBar()

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Servicios Críticos", badge = "NACIONAL")

        Spacer(modifier = Modifier.height(16.dp))

        CriticalServiceItem(
            title = "Policía Nacional",
            number = "105",
            numberColor = Color(0xFF2563EB),
            iconBackgroundColor = if (isSystemInDarkTheme())
                Color(0xFF1E3A8A)
            else
                Color(0xFFE0ECFF),
            iconTint = Color(0xFF2563EB)
        )

        CriticalServiceItem(
            title = "Bomberos",
            number = "116",
            numberColor = Color(0xFFDC2626),
            iconBackgroundColor = if (isSystemInDarkTheme())
                Color(0xFF3F1D1D)
            else
                Color(0xFFFDE2E2),
            iconTint = Color(0xFFDC2626)
        )
        CriticalServiceItem(
            title = "SAMU (Minsa)",
            number = "106",
            numberColor = Color(0xFF16A34A),
            iconBackgroundColor = if (isSystemInDarkTheme())
                Color(0xFF0F3D2E)
            else
                Color(0xFFD2F5D4),
            iconTint = Color(0xFF16A34A)
        )

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Otros Servicios")

        Spacer(modifier = Modifier.height(16.dp))

        OtherServicesGrid()

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SectionTitle(title: String, badge: String? = null) {

    Row(verticalAlignment = Alignment.CenterVertically) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiaryContainer,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        badge?.let {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun OtherServicesGrid() {

    Column {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SmallServiceCard(
                title = "Línea 100",
                subtitle = "Violencia familiar y sexual",
                icon = Icons.Default.Female,
                iconTint = Color(0xFFE91E63),
                iconBackground = Color(0xFFFCE4EC),
                modifier = Modifier.weight(1f),
                index = 0
            )

            SmallServiceCard(
                title = "Indeci",
                subtitle = "Defensa Civil nacional",
                icon = Icons.Default.Warning,
                iconTint = Color(0xFFF57C00),
                iconBackground = Color(0xFFFFF3E0),
                modifier = Modifier.weight(1f),
                index = 1
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SmallServiceCard(
                title = "EsSalud",
                subtitle = "Citas y urgencias médicas",
                icon = Icons.Default.LocalHospital,
                iconTint = Color(0xFF1976D2),
                iconBackground = Color(0xFFE3F2FD),
                modifier = Modifier.weight(1f),
                index = 2
            )

            SmallServiceCard(
                title = "Cruz Roja",
                subtitle = "Rescate y apoyo médico",
                icon = Icons.Default.Favorite,
                iconTint = Color(0xFFD32F2F),
                iconBackground = Color(0xFFFFEBEE),
                modifier = Modifier.weight(1f),
                index = 3
            )
        }
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
            isBodyScrollable = false,
            body = {
                EmergencyBody(
                    onIntent = {},
                    uiState = EmergencyUiState(
                        section = ResultState.Success(
                            SectionEntity(
                                status = 200,
                                data = listOf(
                                    SectionItemEntity(
                                        title = "Seguridad Ciudadana",
                                        image = "https://example.com/image1.jpg",
                                        type = "local_security"
                                    ),
                                    SectionItemEntity(
                                        title = "Bomberos",
                                        image = "https://example.com/image2.jpg",
                                        type = "firefighter"
                                    ),
                                    SectionItemEntity(
                                        title = "Policía Nacional",
                                        image = "https://example.com/image3.jpg",
                                        type = "police"
                                    )
                                )
                            )
                        )
                    )
                )
            },
            footer = {
                HomeBottomBar(rememberNavController())
            },
        )
    }
}