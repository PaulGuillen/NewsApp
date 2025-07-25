package com.devpaul.shared.ui.components.molecules

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
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

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    onLogoutClick: (() -> Unit)? = null,
    mustBeCentered: Boolean = false,
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
            title = {
                if (mustBeCentered) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            actions = actions,
            navigationIcon = {
                if (!mustBeCentered) {
                    onBackClick?.let {
                        IconButton(onClick = it) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Atrás"
                            )
                        }
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
        onBackClick = { }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarWithLogoutPreview() {
    TopBar(
        title = "InfoPerú",
        onBackClick = { },
        onLogoutClick = { }
    )
}