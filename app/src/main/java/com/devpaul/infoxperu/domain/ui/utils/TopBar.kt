package com.devpaul.infoxperu.domain.ui.utils

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onMenuClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {
        IconButton(onClick = onLogoutClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = "Salir sesión"
            )
        }
    }
) {
    Surface(
        modifier = Modifier.shadow(elevation = 6.dp, shape = RoundedCornerShape(0.dp)),
        color = MaterialTheme.colorScheme.surface
    ) {
        TopAppBar(
            title = { Text(text = title, fontWeight = FontWeight.Bold) },
            actions = actions,
            navigationIcon = {
                IconButton(onClick = onMenuClick) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar(
        title = "InfoPerú",
        onLogoutClick = { /* Acción de salir sesión */ }
    )
}

