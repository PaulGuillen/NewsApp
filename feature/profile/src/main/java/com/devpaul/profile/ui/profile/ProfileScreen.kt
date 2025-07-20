package com.devpaul.profile.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_data.Screen
import com.devpaul.core_data.util.Constant
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.profile.domain.entity.ProfileUserEntity
import com.devpaul.profile.ui.profile.components.ProfileOptionItem
import com.devpaul.shared.data.skeleton.SkeletonRenderer
import com.devpaul.shared.data.skeleton.SkeletonType
import com.devpaul.shared.ui.components.atoms.base.button.CustomButton
import com.devpaul.shared.ui.components.atoms.base.image.ProfileImagePicker
import com.devpaul.shared.ui.components.molecules.BottomNavigationBar
import com.devpaul.shared.ui.components.molecules.TopBarPrincipal
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
    ) { _, uiState, onIntent, _, _ ->
        BaseContentLayout(
            isBodyScrollable = true,
            header = {
                TopBarPrincipal(
                    style = 3,
                    title = "Mi Cuenta"
                )
            },
            body = {
                ProfileContent(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    onIntent = onIntent,
                    onNavigate = { route ->
                        navController.navigate(route)
                    },
                    uiState = uiState
                )
            },
            footer = {
                BottomNavigationBar(navController)
            },
        )
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigate: (String) -> Unit = {},
    onIntent: (ProfileUiIntent) -> Unit = {},
    uiState: ProfileUiState
) {
    var profile: ProfileUserEntity?
    var fullName: String
    var email: String

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            when (val state = uiState.profile) {
                is ResultState.Loading -> {
                    SkeletonRenderer(type = SkeletonType.PROFILE_ACCOUNT)
                }

                is ResultState.Success -> {
                    profile = state.response.data
                    fullName = "${profile?.name} ${profile?.lastname}"
                    email = profile?.email.toString()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileImagePicker(
                            defaultImageUrl = Constant.URL_IMAGE,
                            base64Image = profile?.image,
                            showDialogOnClick = false,
                            onImageSelected = { _, _ -> }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(fullName, style = MaterialTheme.typography.titleMedium)
                        Text(email, style = MaterialTheme.typography.bodySmall)

                        Spacer(modifier = Modifier.height(8.dp))

                        CustomButton(
                            modifier = Modifier.fillMaxWidth(0.6f),
                            text = "Editar Perfil",
                            onClick = {
                                navController.navigate(Screen.ProfileUpdate.route)
                            }
                        )
                    }
                }

                is ResultState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileImagePicker(
                            defaultImageUrl = Constant.URL_IMAGE,
                            base64Image = null,
                            showDialogOnClick = false,
                            onImageSelected = { _, _ -> }
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Error al cargar perfil",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        CustomButton(
                            modifier = Modifier.fillMaxWidth(0.6f),
                            text = "Reintentar",
                            onClick = {
                                onIntent(ProfileUiIntent.GetUserProfile)
                            }
                        )
                    }
                }

                else -> {}
            }

            Spacer(modifier = Modifier.height(28.dp))

            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    val options = listOf(
                        Triple("Recomendar a un amigo", Icons.Outlined.Share) {
                            onIntent(ProfileUiIntent.ShareApp)
                        },
                        Triple("Términos de servicio", Icons.Outlined.Build) {
                            onIntent(ProfileUiIntent.OpenTerms)
                        },
                        Triple("Política de privacidad", Icons.Outlined.Settings) {
                            onIntent(ProfileUiIntent.OpenPrivacy)
                        },
                        Triple("Sugerencias", Icons.Outlined.MailOutline) {
                            onNavigate("profile/suggestions")
                        },
                        Triple("Acerca de nosotros", Icons.Outlined.Home) {
                            onNavigate("profile/about")
                        }
                    )

                    options.forEachIndexed { _, (title, icon, action) ->
                        ProfileOptionItem(
                            title = title,
                            icon = icon,
                            onClick = { action() }
                        )
                    }

                    ProfileOptionItem(
                        title = "Salir",
                        icon = Icons.AutoMirrored.Outlined.ExitToApp,
                        color = BrickRed,
                        showDivider = false,
                        onClick = {
                            onIntent(ProfileUiIntent.Logout)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    BaseContentLayout(
        header = {
            TopBarPrincipal(
                style = 3,
                title = "Mi Cuenta"
            )
        },
        body = {
            ProfileContent(
                modifier = Modifier.fillMaxSize(),
                navController = rememberNavController(),
                onNavigate = {},
                onIntent = {},
                uiState = ProfileUiState(profile = ResultState.Loading)
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileErrorContentPreview() {
    BaseContentLayout(
        header = {
            TopBarPrincipal(
                style = 3,
                title = "Mi Cuenta"
            )
        },
        body = {
            ProfileContent(
                modifier = Modifier.fillMaxSize(),
                navController = rememberNavController(),
                onNavigate = {},
                onIntent = {},
                uiState = ProfileUiState(profile = ResultState.Error("Error al cargar perfil"))
            )
        },
    )
}