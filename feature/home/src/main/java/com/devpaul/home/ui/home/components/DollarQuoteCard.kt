package com.devpaul.home.ui.home.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.White
import com.devpaul.home.data.datasource.mock.DollarQuoteMock
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.shared.ui.components.atoms.base.DividerView
import com.devpaul.shared.ui.components.atoms.skeleton.SkeletonDollarQuote

@Composable
fun DollarQuoteCard(
    context: Context,
    dollarQuoteState: ResultState<DollarQuoteEntity>,
    onRetry: () -> Unit,
) {
    when (dollarQuoteState) {
        is ResultState.Loading -> {
            SkeletonDollarQuote()
        }

        is ResultState.Success -> {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                onClick = {
                    val intent =
                        Intent(Intent.ACTION_VIEW, dollarQuoteState.response.data.link?.toUri())
                    context.startActivity(intent)
                }
            ) {
                Column {
                    Image(
                        painter = rememberAsyncImagePainter(dollarQuoteState.response.data.imageUrl),
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
                                    painter = rememberAsyncImagePainter(dollarQuoteState.response.data.iconImage),
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
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = dollarQuoteState.response.data.prices?.firstOrNull()?.buy?.toString()
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
                            )
                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = dollarQuoteState.response.data.prices?.firstOrNull()?.sell.toString(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = dollarQuoteState.response.data.site ?: "Sitio no disponible",
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
                            text = dollarQuoteState.response.data.date ?: "Fecha no disponible",
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
        }

        is ResultState.Error -> {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = dollarQuoteState.message,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )

                        Button(
                            onClick = onRetry,
                            shape = RectangleShape,
                            elevation = ButtonDefaults.buttonElevation(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = White
                            )
                        ) {
                            Text(text = "Reintentar", fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        else -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun DollarQuoteLoadingCardPreview() {
    DollarQuoteCard(
        context = LocalContext.current,
        dollarQuoteState = ResultState.Loading,
        onRetry = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DollarQuoteSuccessCardPreview() {
    DollarQuoteCard(
        context = LocalContext.current,
        dollarQuoteState = ResultState.Success(DollarQuoteMock().dollarQuoteMock),
        onRetry = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DollarQuoteErrorCardPreview() {
    DollarQuoteCard(
        context = LocalContext.current,
        dollarQuoteState = ResultState.Error("Error al cargar el valor del dólar"),
        onRetry = {}
    )
}