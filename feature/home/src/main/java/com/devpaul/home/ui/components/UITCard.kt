package com.devpaul.home.ui.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.BlueDark
import com.devpaul.core_platform.theme.White
import com.devpaul.home.data.datasource.mock.UITMock
import com.devpaul.home.domain.entity.UITEntity
import com.devpaul.shared.components.atoms.DividerView
import com.devpaul.shared.ui.skeleton.UITCardSkeleton

@Composable
fun UITCard(
    context: Context,
    uitState: ResultState<UITEntity>
) {
    when (uitState) {
        is ResultState.Loading -> {
            UITCardSkeleton()
        }

        is ResultState.Success -> {
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
                    val intent = Intent(Intent.ACTION_VIEW, uitState.response.data.link?.toUri())
                    context.startActivity(intent)
                }
            ) {
                Column(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(24.dp)) {
                            Image(
                                painter = rememberAsyncImagePainter(uitState.response.data.iconImage),
                                contentDescription = "Icono del dólar"
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = uitState.response.data.service ?: "Servicio no disponible",
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
                            text = uitState.response.data.value.toString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = uitState.response.data.site ?: "Sitio no disponible",
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
                        text = uitState.response.data.year.toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                    )

                }
            }
        }

        is ResultState.Error -> {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = White,
                    contentColor = Black
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = uitState.message,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        ),
                        color = BlueDark,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UITCardSuccessPreview() {
    UITCard(
        context = LocalContext.current,
        uitState = ResultState.Success(UITMock().uitMock)
    )
}

@Preview(showBackground = true)
@Composable
fun UITCardErrorPreview() {
    UITCard(
        context = LocalContext.current,
        uitState = ResultState.Error("Error al cargar la UIT")
    )
}