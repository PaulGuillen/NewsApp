package com.devpaul.home.ui.home.components.latestgeneral

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.ui.home.HomeUiState

@Composable
fun LatestGeneralSection(uiState: HomeUiState) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Update,
                contentDescription = "Últimas",
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Últimas Actualizaciones",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiaryContainer,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Holiday alert
        when (val state = uiState.holidayAlert) {
            is ResultState.Success -> {
                val h = state.response
                LatestGeneralItem(
                    category = "FERIADOS",
                    title = h.title?.takeIf { it.isNotBlank() } ?: h.service
                    ?: "Alerta de feriados",
                    body = h.alert ?: "No hay alerta disponible",
                    footer = "Fuente: ${h.source ?: "-"} • ${h.date ?: "-"}")
            }

            is ResultState.Loading -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .heightIn(min = 96.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
                    border = androidx.compose.foundation.BorderStroke(
                        0.1.dp,
                        MaterialTheme.colorScheme.outline
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Cargando...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            is ResultState.Error -> LatestGeneralItem(
                category = "FERIADOS",
                title = "Error",
                body = state.message,
                footer = ""
            )

            else -> LatestGeneralItem(
                category = "SUNAT",
                title = "Error",
                body = "Error",
                footer = ""
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // SUNAT
        when (val state = uiState.sunatExchangeRate) {
            is ResultState.Success -> {
                val s = state.response
                LatestGeneralItem(
                    category = "SUNAT",
                    title = "Tipo de cambio ${s.currency ?: "USD"}",
                    body = "Compra: ${s.buy ?: "-"} • Venta: ${s.sell ?: "-"}",
                    footer = s.date ?: "-"
                )
            }

            is ResultState.Loading -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .heightIn(min = 96.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
                    border = androidx.compose.foundation.BorderStroke(
                        0.1.dp,
                        MaterialTheme.colorScheme.outline
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Cargando...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            is ResultState.Error -> LatestGeneralItem(
                category = "SUNAT",
                title = "Error",
                body = state.message,
                footer = ""
            )

            else -> LatestGeneralItem(
                category = "SUNAT",
                title = "Error",
                body = "Error",
                footer = ""
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}