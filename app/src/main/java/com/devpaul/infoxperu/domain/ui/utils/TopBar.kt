package com.devpaul.infoxperu.domain.ui.utils

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
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
    onBackClick: (() -> Unit)? = null, // Hacer el botón de retroceso opcional
    onLogoutClick: (() -> Unit)? = null, // Hacer el botón de logout opcional
    actions: @Composable RowScope.() -> Unit = {
        if (onLogoutClick != null) {
            IconButton(onClick = onLogoutClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Salir sesión"
                )
            }
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
                onBackClick?.let {
                    IconButton(onClick = it) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
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
        onBackClick = { /* Acción de retroceder */ }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarWithLogoutPreview() {
    TopBar(
        title = "InfoPerú",
        onBackClick = { /* Acción de retroceder */ },
        onLogoutClick = { /* Acción de salir sesión */ }
    )
}

