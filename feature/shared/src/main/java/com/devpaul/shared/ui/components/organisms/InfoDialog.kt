package com.devpaul.shared.ui.components.organisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.White

@Composable
fun InfoDialog(
    title: String,
    description: String,
    imageUrl: String,
    onDismiss: () -> Unit,
    onNavigate: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        containerColor = White,
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 8.dp,
        text = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ir",

                        modifier = Modifier.clickable { onNavigate() },
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "X",
                        modifier = Modifier.clickable { onDismiss() },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Black
                    )
                }


                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Imagen de noticia",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(White)
                ) {
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            maxLines = 6,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Justify
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun InfoDialogPreview() {
    InfoDialog(
        title = "Distritos",
        description = "Aquí podrás consultar todos los distritos de Lima con sus respectivos números telefónicos de contacto para la Policía Nacional del Perú, Bomberos y Serenazgo. Esta información es útil en caso de emergencias o situaciones que requieran atención inmediata en tu distrito. Información adicional...",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5f/Vinicunca%2C_Peru.jpg/800px-Vinicunca%2C_Peru.jpg",
        onDismiss = {},
        onNavigate = {}
    )
}