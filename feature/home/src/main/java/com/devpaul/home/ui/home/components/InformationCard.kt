package com.devpaul.home.ui.home.components

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.BlueDark
import com.devpaul.core_platform.theme.White
import com.devpaul.home.data.datasource.mock.DollarQuoteMock
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.shared.screen.atomic.DividerView
import com.devpaul.shared.ui.skeleton.SkeletonInformationCard

@Composable
fun InformationCard(
    context: Context,
    dollarQuote: DollarQuoteEntity?,
    dollarQuoteError: String? = null,
    dollarQuoteLoading: Boolean = false,
) {
    if (dollarQuoteLoading) {
        SkeletonInformationCard()
    } else {
        InformationCardContent(
            context = context,
            dollarQuote = dollarQuote,
            dollarQuoteError = dollarQuoteError
        )
    }
}

@Composable
fun InformationCardContent(
    context: Context,
    dollarQuote: DollarQuoteEntity?,
    dollarQuoteError: String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.run {
            cardColors(
                containerColor = White,
                contentColor = Black
            )
        },
        onClick = {
            if (dollarQuote != null) {
                val intent = Intent(Intent.ACTION_VIEW, dollarQuote.data.link?.toUri())
                context.startActivity(intent)
            }
        }
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

                if (dollarQuote != null) {
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
                            text = dollarQuote.data.prices?.firstOrNull()?.buy?.toString()
                                ?: "N/A",
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
                            text = dollarQuote.data.prices?.firstOrNull()?.sell.toString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = dollarQuote.data.site ?: "Sitio no disponible",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DividerView()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = dollarQuote.data.date ?: "Fecha no disponible",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                } else {
                    Text(
                        text = dollarQuoteError ?: "Error al obtener datos del dólar.",
                        color = MaterialTheme.colorScheme.error,
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

@Preview(showBackground = true)
@Composable
fun InformationCardPreview() {
    InformationCardContent(
        context = LocalContext.current,
        dollarQuote = DollarQuoteMock().dollarQuoteMock,
        dollarQuoteError = null
    )
}

@Preview(showBackground = true)
@Composable
fun InformationCardErrorPreview() {
    InformationCardContent(
        context = LocalContext.current,
        dollarQuote = null,
        dollarQuoteError = "Error al cargar el valor del dólar."
    )
}