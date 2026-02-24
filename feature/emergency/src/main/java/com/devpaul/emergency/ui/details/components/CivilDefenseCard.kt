package com.devpaul.emergency.ui.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devpaul.emergency.domain.entity.GeneralEntityItem
import com.devpaul.emergency.ui.details.DetailsUiIntent
import com.devpaul.shared.domain.extractPhones

@Composable
fun CivilDefenseCard(
    item: GeneralEntityItem,
    onIntent: (DetailsUiIntent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = item.key,
                style = MaterialTheme.typography.titleLarge
            )

            val phones: List<String> = remember(item) {
                item.value.flatMap { extractPhones(it) }.distinct()
            }

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