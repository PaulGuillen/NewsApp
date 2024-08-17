package com.devpaul.infoxperu.domain.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.domain.screen.atomic.DividerView
import com.devpaul.infoxperu.ui.theme.BlueDark
import androidx.compose.material3.*
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.CotizacionItem
import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse

@Composable
fun InformationCard(
    dollarQuoteState: ResultState<DollarQuoteResponse>?,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.background_dolar_home),
                contentDescription = "Valor del dólar",
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(24.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.dollar),
                            contentDescription = "Icono del dólar"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Valor del dólar (USD)",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        ),
                        color = BlueDark,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                when (dollarQuoteState) {
                    is ResultState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }

                    is ResultState.Success -> {
                        val data = dollarQuoteState.data

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Compra:",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                ),
                                color = BlueDark
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = data.cotizacion?.firstOrNull()?.compra?.toString() ?: "N/A",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Venta:",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = BlueDark
                            )
                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = data.cotizacion?.firstOrNull()?.venta.toString(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = data.sitio ?: "Sitio no disponible",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        DividerView()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = data.fecha ?: "Fecha no disponible",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    is ResultState.Error -> {
                        Text(
                            text = "Error al obtener datos del dólar.",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 15.sp
                            ),
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    else -> {
                        Text(
                            text = "Sin datos disponibles",
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 15.sp
                            ),
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InformationCardPreview() {
    InformationCard(
        dollarQuoteState = ResultState.Success(
            DollarQuoteResponse(
                cotizacion = listOf(
                    CotizacionItem(
                        compra = 3.61,
                        venta = 3.72
                    )
                ),
                fecha = "2021-10-10",
                sitio = "https://www.google.com"
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun InformationCardErrorPreview() {
    InformationCard(
        dollarQuoteState = ResultState.Error(Exception("Simulated Error"))
    )
}

@Preview(showBackground = true)
@Composable
fun InformationCardNoDataPreview() {
    InformationCard(
        dollarQuoteState = null
    )
}
