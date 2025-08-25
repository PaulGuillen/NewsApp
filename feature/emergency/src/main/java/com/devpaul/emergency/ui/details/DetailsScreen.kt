package com.devpaul.emergency.ui.details

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.emergency.domain.entity.GeneralEntityItem
import com.devpaul.shared.domain.rememberCallActions
import com.devpaul.shared.domain.splitNumberAndNote
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
    if (uiState.generalCase) {
        GeneralCase(uiState = uiState, onIntent = onIntent)
    } else if (uiState.civilCase) {
        CivilDefenseCase(uiState = uiState)
    } else if (uiState.securityCase) {
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
        is ResultState.Loading -> { /* shimmer */
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

        is ResultState.Error -> { /* error */
        }

        else -> Unit
    }
}

@Composable
fun EmergencyCard(
    item: GeneralEntityItem,
    onIntent: (DetailsUiIntent) -> Unit
) {
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

            item.value.forEachIndexed { index, raw ->
                val (phone, note) = splitNumberAndNote(raw)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = "Contacto ${index + 1}: $phone",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (note != null) {
                            Text(
                                text = note,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Botón fijo a la derecha
                    Button(
                        onClick = { onIntent(DetailsUiIntent.CallNumber(phone)) },
                        modifier = Modifier.height(36.dp)
                    ) { Text("Llamar") }
                }
            }
        }
    }
}

@Composable
fun CivilDefenseCase(
    uiState: DetailsUiState,
) {
    when (val state = uiState.civilDefense) {
        is ResultState.Loading -> {
            // Show loading state
        }

        is ResultState.Success -> {
            // Display civil defense case details
        }

        is ResultState.Error -> {
            // Show error message
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