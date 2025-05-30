package com.devpaul.infoxperu.feature.util.ui.home_screen

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.UITResponse
import com.devpaul.infoxperu.domain.screen.atomic.DividerView
import com.devpaul.infoxperu.feature.util.ui.skeleton.UITCardSkeleton
import com.devpaul.infoxperu.ui.theme.Black
import com.devpaul.infoxperu.ui.theme.BlueDark
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun UITCard(uitState: ResultState<UITResponse>?, context: Context) {
    var showSkeleton by remember { mutableStateOf(true) }

    LaunchedEffect(uitState) {
        showSkeleton = uitState is ResultState.Loading
    }

    if (showSkeleton) {
        UITCardSkeleton()
    } else {
        UITCardContent(uitState, context)
    }
}

@Composable
fun UITCardContent(uitState: ResultState<UITResponse>?, context: Context) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = White,
            contentColor = Black
        ),
        onClick = {
            if (uitState is ResultState.Success) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uitState.data.enlace))
                context.startActivity(intent)
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))
            when (uitState) {
                is ResultState.Success -> {
                    val data = uitState.data

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(24.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.change_management),
                                contentDescription = "Icono del dólar"
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = data.servicio ?: "Servicio no disponible",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            ),
                            color = BlueDark,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "UIT:",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = BlueDark
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = data.UIT.toString(),
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
                            .padding(end = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DividerView()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = data.periodo.toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                    )
                }

                is ResultState.Error -> {
                    Text(
                        text = "Error al obtener el UIT",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                else -> {
                    Text(
                        text = "No hay información disponible",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UITCardSuccessPreview() {
    UITCardContent(
        ResultState.Success(
            UITResponse(
                servicio = "Valor del UIT",
                UIT = 5150.0,
                periodo = 2024,
                sitio = "DePeru.com"
            )
        ),
        context = LocalContext.current
    )
}

@Preview(showBackground = true)
@Composable
fun UITCardErrorPreview() {
    UITCardContent(
        uitState = ResultState.Error(Exception("Simulated Error")),
        context = LocalContext.current
    )
}

@Preview(showBackground = true)
@Composable
fun UITCardNotDataAvailablePreview() {
    UITCardContent(
        uitState = null,
        context = LocalContext.current
    )
}
