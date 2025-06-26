package com.devpaul.profile.ui.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.White
import com.devpaul.profile.ui.profile.components.ProfileOptionItem
import com.devpaul.shared.domain.handleDefaultErrors
import com.devpaul.shared.ui.components.atoms.base.CustomButton
import com.devpaul.shared.ui.components.molecules.BottomNavigationBar
import com.devpaul.shared.ui.components.molecules.TopBar
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(navController: NavHostController) {
    val viewModel: ProfileViewModel = koinViewModel()
    val context = LocalContext.current

    fun validateAndUpdate() {

    }

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navController,
        onDefaultError = { error, showSnackBar ->
            handleDefaultErrors(error, showSnackBar)
        }
    ) { _, uiState, onIntent, _, _ ->
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.app_name)
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            },
        ) { innerPadding ->
            ProfileContent(
                context = context,
                modifier = Modifier.fillMaxSize(),
                innerPadding = innerPadding,
                onNavigate = { route ->
                    navController.navigate(route)
                },
                onLogout = {
                    // Handle logout logic here
                }
            )
        }
    }
}

@Composable
fun ProfileContent(
    context: Context,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    onNavigate: (String) -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = rememberAsyncImagePainter("https://img.freepik.com/premium-vector/default-avatar-profile-icon-social-media-user-image-gray-avatar-icon-blank-profile-silhouette-vector-illustration_561158-3407.jpg"),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                )
                Icon(
                    imageVector = Icons.Outlined.Face,
                    contentDescription = "Cambiar foto",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(40.dp)
                        .background(BrickRed, CircleShape)
                        .padding(2.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(0.7f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Charlotte King",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "@johnkinggraphics",
                    style = MaterialTheme.typography.bodySmall,
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomButton(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    text = "Edit Profile",
                    onClick = { onNavigate("profile/update") }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                val options = listOf(
                    Triple("Recomendar a un amigo", Icons.Outlined.Share, "profile/share"),
                    Triple("Términos de servicio", Icons.Outlined.Build, "profile/terms"),
                    Triple("Política de privacidad", Icons.Outlined.Settings, "profile/privacy"),
                    Triple("Sugerencias", Icons.Outlined.MailOutline, "profile/suggestions"),
                    Triple("Acerca de nosotros", Icons.Outlined.Home, "profile/about")
                )

                options.forEachIndexed { index, (title, icon, route) ->
                    val isLastItem = index == options.lastIndex
                    ProfileOptionItem(
                        title = title,
                        icon = icon,
                        showDivider = !isLastItem,
                        onClick = { onNavigate(route) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                ProfileOptionItem(
                    title = "Salir",
                    icon = Icons.AutoMirrored.Outlined.ExitToApp,
                    color = BrickRed,
                    showDivider = false,
                    onClick = {
                        onLogout()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    ProfileContent(
        context = LocalContext.current,
        modifier = Modifier.fillMaxSize(),
        innerPadding = PaddingValues(0.dp),
    )
}
