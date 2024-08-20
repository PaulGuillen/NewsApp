package com.devpaul.infoxperu.domain.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

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

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar(
        title = "InfoPerú",
        onLogoutClick = { /* Acción de salir sesión */ }
    )
}
