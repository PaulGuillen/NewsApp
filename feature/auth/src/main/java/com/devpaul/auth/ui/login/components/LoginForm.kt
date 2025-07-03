package com.devpaul.auth.ui.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.auth.domain.entity.Login
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.extension.validateEmail
import com.devpaul.core_platform.extension.validateStartSession
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.White
import com.devpaul.shared.ui.components.atoms.base.ScreenLoading
import com.devpaul.shared.ui.components.atoms.base.button.CustomButton
import com.devpaul.shared.ui.components.atoms.base.textfield.CustomOutlinedTextField
import com.devpaul.shared.ui.components.atoms.base.textfield.PasswordField
import com.devpaul.shared.ui.components.organisms.BaseContentLayout

@Composable
fun LoginForm(
    navHostController: NavHostController,
    onLogin: (String, String) -> Unit,
    onForgotPassword: (String) -> Unit,
    showSnackBar: (String) -> Unit,
    uiState: ResultState<Login>?,
    onSuccess: (() -> Unit)? = null,
    onError: () -> Unit = {},
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    fun validateLogin() {
        val validationResult = validateStartSession(context, email, password)
        if (validationResult != null) {
            showSnackBar(validationResult)
        } else {
            onLogin(email, password)
        }
    }

    fun validateRecoveryPassword() {
        val validationResult = validateEmail(context, email)
        if (validationResult != null) {
            showSnackBar(validationResult)
        } else {
            onForgotPassword(email)
        }
    }

    BaseContentLayout(
        body = {
            LoginBody(
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
                onLoginClick = ::validateLogin,
                onForgotPasswordClick = ::validateRecoveryPassword,
                rememberMe = rememberMe,
                onRememberMeChange = { rememberMe = it }
            )
        },
        footer = {
            LoginFooter(
                navHostController = navHostController
            )
        },
    )

    when (uiState) {
        is ResultState.Loading -> {
            ScreenLoading()
        }

        is ResultState.Error -> {
            showSnackBar(uiState.message)
        }

        else -> {}
    }
}

@Composable
fun LoginBody(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    rememberMe: Boolean,
    onRememberMeChange: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_info_peru),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .size(280.dp)
                .padding(bottom = 20.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    text = "Bienvenido a Info Perú",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Ingresa con tus credenciales para iniciar sesión",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )

                HorizontalDivider(
                    thickness = 1.5.dp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                CustomOutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    labelRes = R.string.register_screen_email,
                    leadingIcon = Icons.Default.Email,
                    enabled = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                PasswordField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = "Contraseña",
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityChange = onPasswordVisibilityChange
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                modifier = Modifier.offset(x = (-10).dp),
                checked = rememberMe,
                onCheckedChange = { onRememberMeChange(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = BrickRed,
                    uncheckedColor = Color.Black,
                )
            )
            Text(
                modifier = Modifier.offset(x = (-10).dp),
                text = "Recordar usuario/contraseña",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp,
            )
        }


        Spacer(modifier = Modifier.height(10.dp))

        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.login_button),
            onClick = onLoginClick,
            painterIcon = painterResource(id = R.drawable.baseline_self_improvement_24),
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onForgotPasswordClick
        ) {
            Text(
                stringResource(id = R.string.forgot_password),
                modifier = Modifier.offset(x = (10).dp),
                color = BrickRed,
            )
        }

    }
}

@Composable
fun LoginFooter(
    navHostController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.not_have_account),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
            TextButton(onClick = {
                if (navHostController.currentDestination?.route != Screen.Register.route) {
                    navHostController.navigate(Screen.Register.route) {
                        launchSingleTop = true
                    }
                }
            }) {
                Text(
                    text = stringResource(id = R.string.register),
                    color = BrickRed
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BaseContentLayoutPreview() {
    BaseContentLayout(
        header = {},
        body = {
            LoginBody(
                email = "",
                onEmailChange = {},
                password = "",
                onPasswordChange = {},
                passwordVisible = false,
                onPasswordVisibilityChange = {},
                onLoginClick = {},
                onForgotPasswordClick = {},
                rememberMe = false,
                onRememberMeChange = {}
            )
        },
        footer = {
            LoginFooter(navHostController = rememberNavController())
        }
    )
}