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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.CircularProgressIndicator
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
import com.devpaul.core_data.BuildConfig
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.profile.domain.entity.ProfileEntity
import com.devpaul.profile.domain.entity.ProfileUserEntity
import com.devpaul.profile.ui.components.ProfileActionButton
import com.devpaul.profile.ui.components.rememberProfileUiColors
import com.devpaul.profile.ui.profile.components.ProfileItem
import com.devpaul.profile.ui.profile.components.Section
import com.devpaul.shared.domain.buildDisplayName
import com.devpaul.shared.domain.displayOrDefault
import com.devpaul.shared.ui.components.atoms.base.image.ProfileImagePicker
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
                is ProfileUiEvent.LaunchIntent -> context.startActivity(event.intent)
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
            header = { ProfileHeader() },
            body = {
                ProfileContent(
                    uiState = uiState,
                    navController = navController,
                    onIntent = onIntent
                )
            },
            footer = { HomeBottomBar(navController) },
        )
    }
}

@Composable
fun ProfileHeader() {
    AppHeader(
        title = "Emergencias PE",
    )
}

@Composable
fun ProfileContent(
    uiState: ProfileUiState,
    navController: NavHostController,
    onIntent: (ProfileUiIntent) -> Unit
) {
    val colors = rememberProfileUiColors()
    val profileState = uiState.profile

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        when (profileState) {
            is ResultState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ResultState.Error -> {
                Text(
                    text = profileState.message,
                    color = Color(0xFFEF4444),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            is ResultState.Success -> {
                val user = profileState.response.data

                ProfileHero(
                    user = user,
                    textPrimary = colors.primaryText,
                    accentColor = colors.accent
                )

                Spacer(modifier = Modifier.height(24.dp))

                Section("CUENTA", colors.surface) {
                    ProfileItem(
                        title = "Información personal",
                        subtitle = buildDisplayName(user.name, user.lastname),
                        onClick = { navController.navigate(Screen.ProfileUpdate.route) }
                    )
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    ProfileItem(
                        title = "Correo electrónico",
                        subtitle = user.email.displayOrDefault()
                    )
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    ProfileItem(
                        title = "Celular",
                        subtitle = user.phone.displayOrDefault()
                    )
                }

                Section("SOPORTE", colors.surface) {
                    ProfileItem(
                        title = "Sugerencias y comentarios",
                        onClick = { navController.navigate(Screen.Suggestions.route) }
                    )
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    ProfileItem(
                        title = "Compartir aplicación",
                        onClick = { onIntent(ProfileUiIntent.ShareApp) }
                    )
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    ProfileItem(
                        title = "Términos y condiciones",
                        onClick = { onIntent(ProfileUiIntent.OpenTerms) }
                    )
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    ProfileItem(
                        title = "Política de privacidad",
                        onClick = { onIntent(ProfileUiIntent.OpenPrivacy) }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                ProfileActionButton(
                    text = "Cerrar sesión",
                    icon = Icons.AutoMirrored.Filled.Logout,
                    onClick = { onIntent(ProfileUiIntent.Logout) }
                )
            }

            ResultState.Idle, null -> Unit
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Versión " + BuildConfig.VERSION_NAME,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = colors.secondaryText,
            fontSize = 10.sp
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ProfileHero(
    user: ProfileUserEntity,
    textPrimary: Color,
    accentColor: Color
) {
    val initials = buildString {
        user.name.firstOrNull()?.let { append(it.uppercaseChar()) }
        user.lastname.firstOrNull()?.let { append(it.uppercaseChar()) }
    }.ifBlank { "?" }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!user.image.isNullOrBlank()) {
            ProfileImagePicker(
                defaultImageUrl = "",
                base64Image = user.image,
                showDialogOnClick = false,
                onImageSelected = { _, _ -> },
                modifier = Modifier.size(84.dp),
                isCircular = true
            )
        } else {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(accentColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(initials, color = Color.White, fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(buildDisplayName(user.name, user.lastname), color = textPrimary, fontSize = 18.sp)

        Text(
            user.email.displayOrDefault(),
            color = accentColor,
            fontSize = 13.sp
        )
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
            header = { ProfileHeader() },
            body = {
                ProfileContent(
                    uiState = ProfileUiState(
                        profile = ResultState.Success(
                            ProfileEntity(
                                status = 200,
                                message = "ok",
                                data = ProfileUserEntity(
                                    id = "1",
                                    uid = "uid-1",
                                    name = "Paul",
                                    lastname = "Guillen",
                                    phone = "977588535",
                                    birthdate = "20/01/1998",
                                    email = "paulguillen190@gmail.com",
                                    password = "123456",
                                )
                            )
                        )
                    ),
                    navController = rememberNavController(),
                    onIntent = {}
                )
            },
            footer = { HomeBottomBar(rememberNavController()) },
        )
    }
}
