package com.devpaul.profile.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.White
import com.devpaul.profile.domain.entity.ProfileUserEntity
import com.devpaul.profile.ui.profile.components.ProfileOptionItem
import com.devpaul.shared.domain.handleDefaultErrors
import com.devpaul.shared.ui.components.atoms.base.CustomButton
import com.devpaul.shared.ui.components.atoms.base.ScreenLoading
import com.devpaul.shared.ui.components.molecules.BottomNavigationBar
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
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }

                is ProfileUiEvent.GoToEditProfile -> {

                }
            }
        },
        onDefaultError = { error, showSnackBar ->
            handleDefaultErrors(error, showSnackBar)
        }
    ) { _, uiState, onIntent, _, _ ->
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController)
            },
        ) { innerPadding ->
            ProfileContent(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                innerPadding = innerPadding,
                onIntent = onIntent,
                onNavigate = { route ->
                    navController.navigate(route)
                },
                uiState = uiState
            )
        }
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues,
    onNavigate: (String) -> Unit = {},
    onIntent: (ProfileUiIntent) -> Unit = {},
    uiState: ProfileUiState
) {
    var profile: ProfileUserEntity? = null
    var fullName = "Cargando..."
    var email = ""

    when (val state = uiState.profile) {
        is ResultState.Success -> {
            profile = state.response.data
            fullName = "${profile.name} ${profile.lastname}"
            email = profile.email
        }

        is ResultState.Error -> {
            fullName = "Error al cargar"
        }

        is ResultState.Loading -> {
            ScreenLoading()
            fullName = "Cargando..."
        }

        else -> {}
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(140.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(
                        painter = rememberAsyncImagePainter("https://img.freepik.com/premium-vector/default-avatar-profile-icon-social-media-user-image-gray-avatar-icon-blank-profile-silhouette-vector-illustration_561158-3407.jpg"),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                    Icon(
                        imageVector = Icons.Outlined.Face,
                        contentDescription = "Cambiar foto",
                        tint = Color.White,
                        modifier = Modifier
                            .size(36.dp)
                            .background(BrickRed, CircleShape)
                            .padding(6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(fullName, style = MaterialTheme.typography.titleMedium)
                Text(email, style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(8.dp))

                CustomButton(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    text = "Edit Profile",
                    onClick = {
                        navController.navigate(
                            Screen.ProfileUpdate.createRoute(
                                profileData = profile
                            )
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                shape = RoundedCornerShape(16.dp)
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
    ProfileContent(
        modifier = Modifier.fillMaxSize(),
        navController = NavHostController(LocalContext.current),
        innerPadding = PaddingValues(0.dp),
        onNavigate = {},
        onIntent = {},
        uiState = ProfileUiState(profile = ResultState.Loading)
    )
}