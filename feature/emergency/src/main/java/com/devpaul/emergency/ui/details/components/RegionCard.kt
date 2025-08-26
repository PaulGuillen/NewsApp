package com.devpaul.emergency.ui.details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// 1) Modelo simple para la selección
enum class Region { Lima, Provincias }

@Composable
fun RegionCard(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    val container = when {
        !enabled -> MaterialTheme.colorScheme.surfaceVariant
        selected -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.surface
    }
    val contentColor = when {
        !enabled -> MaterialTheme.colorScheme.onSurfaceVariant
        selected -> MaterialTheme.colorScheme.onPrimary
        else -> MaterialTheme.colorScheme.onSurface
    }
    val borderColor = when {
        !enabled -> MaterialTheme.colorScheme.outlineVariant
        selected -> MaterialTheme.colorScheme.background
        else -> MaterialTheme.colorScheme.outline
    }

    Card(
        modifier = modifier
            .height(48.dp)
            .semantics {
                role = androidx.compose.ui.semantics.Role.Button
                if (!enabled) disabled()
            },
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = container, contentColor = contentColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, style = MaterialTheme.typography.titleMedium, color = contentColor)
        }
    }
}

// 3) Fila reutilizable (Lima / Provincias)
@Composable
fun RegionChooserRow(
    modifier: Modifier = Modifier,
    selected: Region,
    onSelect: (Region) -> Unit,
    limaEnabled: Boolean = true,
    provinciasEnabled: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RegionCard(
            text = "Lima",
            selected = selected == Region.Lima,
            enabled = limaEnabled,
            onClick = { if (limaEnabled) onSelect(Region.Lima) },
            modifier = Modifier.weight(1f)
        )
        RegionCard(
            text = "Provincias",
            selected = selected == Region.Provincias,
            enabled = provinciasEnabled,
            onClick = { if (provinciasEnabled) onSelect(Region.Provincias) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegionRowPreview() {
    var selected by remember { mutableStateOf(Region.Lima) }
    RegionChooserRow(selected = selected, onSelect = { selected = it })
}

@Preview(showBackground = true)
@Composable
fun RegionCardPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
        RegionCard(text = "Lima", selected = true, onClick = {})
        RegionCard(text = "Provincias", selected = false, onClick = {})
    }
}