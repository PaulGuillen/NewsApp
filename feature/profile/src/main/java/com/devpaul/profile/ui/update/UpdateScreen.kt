package com.devpaul.profile.ui.update

import android.content.Context
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.White
import com.devpaul.shared.ui.components.atoms.base.CustomButton
import com.devpaul.shared.ui.components.atoms.base.DateTextField
import com.devpaul.shared.ui.components.atoms.base.textfield.CustomOutlinedTextField
import com.devpaul.shared.ui.components.atoms.base.textfield.PasswordField
import com.devpaul.shared.ui.components.atoms.base.textfield.PhoneOutlinedTextField
import com.devpaul.shared.ui.components.molecules.TopBar
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdateScreen(
    navController: NavHostController,
    profileData: String?
) {

    val viewModel: UpdateViewModel = koinViewModel()
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    BaseScreenWithState(
        viewModel = viewModel,
        onInit = { _, _ ->
            viewModel.getProfileData(profileData)
        },
        navController = navController,
    ) { _, _, _, _, _ ->
        Scaffold(
            topBar = {
                TopBar(title = stringResource(R.string.app_name))
            },
        ) { innerPadding ->
            UpdateScreenContent(
                viewModel = viewModel,
                context = context,
                innerPadding = innerPadding,
                name = name,
                lastname = lastname,
                phone = phone,
                birthdate = birthdate,
                email = email,
                password = password,
                isPasswordVisible = isPasswordVisible,
                onNameChange = { name = it },
                onLastnameChange = { lastname = it },
                onPhoneChange = { phone = it },
                onBirthdateChange = { birthdate = it },
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onPasswordVisibilityChange = { isPasswordVisible = !isPasswordVisible }
            )
        }
    }
}

@Composable
fun UpdateScreenContent(
    viewModel: UpdateViewModel?,
    context: Context,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    name: String,
    lastname: String,
    phone: String,
    birthdate: String,
    email: String,
    password: String,
    isPasswordVisible: Boolean,
    onNameChange: (String) -> Unit,
    onLastnameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onBirthdateChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://img.freepik.com/premium-vector/default-avatar-profile-icon-social-media-user-image-gray-avatar-icon-blank-profile-silhouette-vector-illustration_561158-3407.jpg"),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                )
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Cambiar foto",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = (-4).dp, y = (-4).dp)
                        .size(32.dp)
                        .background(BrickRed, CircleShape)
                        .padding(6.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    CustomOutlinedTextField(
                        value = name,
                        onValueChange = { onNameChange(it) },
                        labelRes = R.string.register_screen_name,
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomOutlinedTextField(
                        value = lastname,
                        onValueChange = { onLastnameChange(it) },
                        labelRes = R.string.register_screen_last_name,
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PhoneOutlinedTextField(
                        value = phone,
                        onValueChange = { onPhoneChange(it) },
                        labelRes = R.string.register_screen_phone,
                        maxLength = 9,
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    DateTextField(
                        value = birthdate,
                        onDateSelected = { onBirthdateChange(it) },
                        labelRes = R.string.register_screen_birthdate
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomOutlinedTextField(
                        value = email,
                        onValueChange = { onEmailChange(it) },
                        labelRes = R.string.register_screen_email,
                        leadingIcon = Icons.Default.Email,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PasswordField(
                        value = password,
                        onValueChange = { onPasswordChange(it) },
                        label = stringResource(id = R.string.register_screen_password),
                        passwordVisible = isPasswordVisible,
                        onPasswordVisibilityChange = onPasswordVisibilityChange,
                    )

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.button_update),
                onClick = {},
            )

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileUpdateScreenPreview() {
    UpdateScreenContent(
        viewModel = null,
        context = LocalContext.current,
        innerPadding = PaddingValues(0.dp),
        name = "John",
        lastname = "Doe",
        phone = "123456789",
        birthdate = "01/01/2000",
        email = "@example.com",
        password = "password123",
        isPasswordVisible = false,
        onNameChange = {},
        onLastnameChange = {},
        onPhoneChange = {},
        onBirthdateChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onPasswordVisibilityChange = {},
    )
}