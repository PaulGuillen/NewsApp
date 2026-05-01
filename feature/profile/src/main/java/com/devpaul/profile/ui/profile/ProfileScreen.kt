package com.devpaul.profile.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.profile.ui.profile.components.ProfileItem
import com.devpaul.profile.ui.profile.components.Section
import com.devpaul.shared.ui.components.molecules.AppHeader
import com.devpaul.shared.ui.components.molecules.HomeBottomBar
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(navController: NavHostController) {
    val viewModel: ProfileViewModel = koinViewModel()
    val context = LocalContext.current

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
        onUiEvent = { event, _ ->
            when (event) {
                is ProfileUiEvent.LaunchIntent -> {
                    context.startActivity(event.intent)
                }

                is ProfileUiEvent.UserLoggedOut -> {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        },
        observeBackKeys = listOf("shouldReload"),
        onBackResults = { results, _, onIntent ->
            if (results["shouldReload"] == true) {
                onIntent(ProfileUiIntent.GetUserProfile)
            }
        },
    ) { _, uiState, onIntent, _, _ ->
        BaseContentLayout(
            isBodyScrollable = false,
            header = {
                ProfileHeader()
            },
            body = {
                ProfileContent(

                )
            },
            footer = {
                HomeBottomBar(navController)
            },
        )
    }
}

@Composable
fun ProfileHeader() {
    AppHeader(
        title = "Emergencias PE",
        subtitle = "Lunes, 24 de Mayo",
        icon = Icons.Default.Public,
        onNotificationClick = { }
    )
}

@Composable

fun ProfileContent() {

    val isDark = isSystemInDarkTheme()

    val background = if (isDark) Color(0xFF000000) else Color(0xFFFFFFFF)
    val cardColor = if (isDark) Color(0xFF1E293B) else Color(0xFFF3F4F6)
    val textPrimary = if (isDark) Color.White else Color.Black
    val textSecondary = Color(0xFF6B7280)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        // 🔥 HEADER
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(Color(0xFF1976D2), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("JP", color = Color.White, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Juan Pérez", color = textPrimary, fontSize = 18.sp)

            Text(
                "juan.perez@email.pe",
                color = Color(0xFF3B82F6),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .background(
                        Color(0xFF3B82F6).copy(alpha = if (isDark) 0.2f else 0.1f),
                        RoundedCornerShape(20)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    "MIEMBRO PREMIUM",
                    color = Color(0xFF3B82F6),
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔥 SECCIONES
        Section("CUENTA Y SEGURIDAD", cardColor) {
            ProfileItem("Información Personal")
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            ProfileItem("Seguridad y Acceso")
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            ProfileItem("Notificaciones")
        }

        Section("FINANZAS", cardColor) {
            ProfileItem("Preferencias de Moneda", "PEN (Soles Peruanos)")
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            ProfileItem("Mercados Seguidos")
        }

        Section("SOPORTE", cardColor) {
            ProfileItem("Centro de Ayuda")
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            ProfileItem("Términos y Privacidad")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔥 LOGOUT
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (isDark)
                        Color(0xFF7F1D1D).copy(alpha = 0.3f)
                    else
                        Color(0xFFFEE2E2),
                    RoundedCornerShape(12.dp)
                )
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Cerrar Sesión",
                color = Color(0xFFEF4444),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "VERSIÓN 2.4.0 (BUILD 82)",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = textSecondary,
            fontSize = 10.sp
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(
    name = "Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileContentPreview() {
    InfoXPeruTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        BaseContentLayout(
            isBodyScrollable = false,
            header = {
                ProfileHeader()
            },
            body = {
                ProfileContent(

                )
            },
            footer = {
                HomeBottomBar(rememberNavController())
            },
        )
    }
}