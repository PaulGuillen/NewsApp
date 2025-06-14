package com.devpaul.home.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.BlueDark
import com.devpaul.core_platform.theme.White
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.shared.components.atoms.DividerView
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
        if (dollarQuote != null) {
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
                    val intent = Intent(Intent.ACTION_VIEW, dollarQuote.data.link?.toUri())
                    context.startActivity(intent)
                }
            ) {
                Column {
                    Image(
                        painter = rememberAsyncImagePainter(dollarQuote.data.imageUrl),
                        contentDescription = "Imagen del dólar",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        contentScale = ContentScale.Crop
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
                                    painter = rememberAsyncImagePainter(dollarQuote.data.iconImage),
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
                    }
                }
            }
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