package com.devpaul.emergency.ui.details

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.entity.GeneralEntityItem
import com.devpaul.emergency.ui.details.components.Region
import com.devpaul.emergency.ui.details.components.RegionChooserRow
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType
import com.devpaul.shared.domain.extractPhones
import com.devpaul.shared.domain.rememberCallActions
import com.devpaul.shared.ui.components.molecules.TopBarPrincipal
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    navController: NavHostController,
    type: String?,
) {

    val context = LocalContext.current
    val viewModel: DetailsViewModel = koinViewModel()
    val actions = rememberCallActions(
        onDenied = {
            Toast.makeText(context, "Permiso de llamada denegado", Toast.LENGTH_SHORT).show()
        }
    )

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
        onInit = { _, _ ->
            viewModel.getTypeService(type = type)
        },
        onUiEvent = { event, _ ->
            when (event) {
                is DetailsUiEvent.RequireCallPermission -> {
                    actions.openDialer(event.phone)
                }
            }
        }

    ) { _, uiState, onIntent, _, _ ->
        BaseContentLayout(
            isBodyScrollable = false,
            applyBottomPaddingWhenNoFooter = true,
            header = {
                TopBarPrincipal(
                    style = 2,
                    onStartIconClick = { navController.popBackStack() },
                )
            },
            body = {
                DetailBody(
                    uiState = uiState,
                    onIntent = onIntent,
                )
            }
        )
    }
}

@Composable
fun DetailBody(
    uiState: DetailsUiState,
    onIntent: (DetailsUiIntent) -> Unit
) {
    if (uiState.generalCase && !uiState.civilCase && !uiState.securityCase) {
        GeneralCase(uiState = uiState, onIntent = onIntent)
    } else if (uiState.civilCase && !uiState.generalCase && !uiState.securityCase) {
        CivilDefenseCase(uiState = uiState, onIntent = onIntent)
    } else if (uiState.securityCase && !uiState.generalCase && !uiState.civilCase) {
        SecurityCase(uiState = uiState)
    } else {
        // Display other case details
    }
}

@Composable
fun GeneralCase(
    uiState: DetailsUiState,
    onIntent: (DetailsUiIntent) -> Unit
) {
    when (val state = uiState.general) {
        is ResultState.Loading -> {
            SkeletonRenderer(type = SkeletonType.GENERAL_EMERGENCY)
        }

        is ResultState.Success -> {
            val data = state.response.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                data.forEach { item ->
                    EmergencyCard(item = item, onIntent = onIntent)
                }
            }
        }

        is ResultState.Error -> {
            EmergencyError(error = state.message, onIntent = onIntent)
        }

        else -> Unit
    }
}

@Composable
fun EmergencyCard(
    item: GeneralEntityItem,
    onIntent: (DetailsUiIntent) -> Unit
) {
    val phones: List<String> = remember(item) {
        item.value.flatMap { extractPhones(it) }.distinct()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = item.key,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            phones.forEachIndexed { index, phone ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Contacto ${index + 1}: $phone",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                    Button(
                        onClick = { onIntent(DetailsUiIntent.CallNumber(phone)) },
                        modifier = Modifier.height(36.dp)
                    ) { Text("Llamar") }
                }
            }

            if (phones.isEmpty()) {
                Text(
                    text = "Sin números disponibles",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun EmergencyError(
    error: String,
    onIntent: (DetailsUiIntent) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = { onIntent(DetailsUiIntent.CallGeneralService) },
                    modifier = Modifier.height(36.dp)
                ) { Text("Reintentar") }
            }
        }
    }
}

@Composable
fun CivilDefenseCase(
    uiState: DetailsUiState,
    onIntent: (DetailsUiIntent) -> Unit
) {
    when (val state = uiState.civilDefense) {
        is ResultState.Loading -> {
            SkeletonRenderer(type = SkeletonType.CIVIL_DEFENSE_EMERGENCY)
        }

        is ResultState.Success -> {
            val data = state.response.data
            var selected by remember { mutableStateOf(Region.Provincias) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RegionChooserRow(
                    selected = selected,
                    onSelect = { region ->
                        selected = region
                        if (region == Region.Lima) {
                            println("⚡ Mostrar solo Lima")
                        } else if (region == Region.Provincias) {
                            println("⚡ Mostrar solo Provincias")
                        }
                    },
                    limaEnabled = false
                )

                data.forEach { item ->
                    EmergencyCard(item = item, onIntent = onIntent)
                }
            }
        }

        is ResultState.Error -> {
            EmergencyError(error = state.message, onIntent = onIntent)
        }

        else -> Unit
    }
}

@Composable
fun SecurityCase(
    uiState: DetailsUiState,
) {
    when (val state = uiState.limaSecurity) {
        is ResultState.Loading -> {
            // Show loading state
        }

        is ResultState.Success -> {
            // Display security case details
        }

        is ResultState.Error -> {
            // Show error message
        }

        else -> Unit
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmergencyCardPreview() {
    EmergencyCard(
        item = GeneralEntityItem(
            key = "Policía Nacional",
            value = listOf("123456789", "987654321 (Emergencias)", "555-1234 (Oficina)")
        ),
        onIntent = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmergencyErrorPreview() {
    EmergencyError(
        error = "Ha ocurrido un error inesperado",
        onIntent = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CivilDefenseCasePreview() {
    val fakeData = listOf(
        GeneralEntityItem(
            key = "Cusco",
            value = listOf("084 240 658", "984 628 573 (Emergencias)")
        ),
        GeneralEntityItem(
            key = "Arequipa",
            value = listOf("054 430 101", "958 534 755 (Central)")
        )
    )

    CivilDefenseCase(
        uiState = DetailsUiState(
            civilDefense = ResultState.Success(
                GeneralEntity(
                    status = 200,
                    data = fakeData,
                )
            )
        ),
        onIntent = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GeneralCaseLoadingPreview() {
    CivilDefenseCase(
        uiState = DetailsUiState(
            civilDefense = ResultState.Loading
        ),
        onIntent = {}
    )
}
