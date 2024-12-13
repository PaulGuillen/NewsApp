package com.devpaul.infoxperu.feature.user_start.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.validateEmail
import com.devpaul.infoxperu.core.extension.validateStartSession
import com.devpaul.infoxperu.domain.screen.BaseScreen
import com.devpaul.infoxperu.domain.ui.utils.ScreenLoading
import com.devpaul.infoxperu.feature.user_start.Screen
import com.devpaul.infoxperu.ui.theme.BrickRed
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val uiEvent by viewModel.uiEvent.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    BaseScreen { _, showSnackBar ->
        LaunchedEffect(Unit) {
            viewModel.executeUiIntent(LoginUiIntent.CheckUserLoggedIn)
        }

        LaunchedEffect(uiEvent) {
            when (uiEvent) {
                is LoginUiEvent.LoginSuccess -> {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
                is LoginUiEvent.LoginError -> showSnackBar((uiEvent as LoginUiEvent.LoginError).error)
                is LoginUiEvent.RecoveryPasswordSuccess -> showSnackBar((uiEvent as LoginUiEvent.RecoveryPasswordSuccess).message)
                is LoginUiEvent.RecoveryPasswordError -> showSnackBar((uiEvent as LoginUiEvent.RecoveryPasswordError).error)
                else -> Unit
            }
            viewModel.resetUiEvent()
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                ScreenLoading()
            }

            LoginContent(navController = navController, onLogin = { email, password ->
                viewModel.executeUiIntent(LoginUiIntent.Login(email.trim(), password.trim()))
            }, onForgotPassword = { email ->
                viewModel.executeUiIntent(LoginUiIntent.ResetPassword(email.trim()))
            }, showSnackBar = { message ->
                showSnackBar(message)
            })
        }
    }
}

@Composable
fun LoginContent(
    navController: NavHostController,
    onLogin: (String, String) -> Unit,
    showSnackBar: (String) -> Unit,
    onForgotPassword: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    fun validateRecoveryPassword() {
        val validationResult = validateEmail(
            context, email,
        )
        if (validationResult != null) {
            showSnackBar(validationResult)
        } else {
            onForgotPassword(email)
        }
    }

    fun validateLogin() {
        val validationResult = validateStartSession(
            context, email, password
        )
        if (validationResult != null) {
            showSnackBar(validationResult)
        } else {
            onLogin(email, password)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_info_peru),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.size(280.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.login_title),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.email_label)) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_email_24),
                    contentDescription = stringResource(id = R.string.email_label)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BrickRed,
                cursorColor = BrickRed,
                focusedLabelColor = BrickRed
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.register_screen_password)) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                val image = if (passwordVisible)
                    painterResource(id = R.drawable.baseline_visibility_24)
                else
                    painterResource(id = R.drawable.baseline_visibility_off_24)

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = image, contentDescription = null)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BrickRed,
                cursorColor = BrickRed,
                focusedLabelColor = BrickRed
            ),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                validateLogin()
            },
            modifier = Modifier.wrapContentSize(),
            shape = RectangleShape,
            elevation = ButtonDefaults.buttonElevation(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrickRed,
                contentColor = White
            )
        ) {
            Text(stringResource(id = R.string.login_button))
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = {
                validateRecoveryPassword()
            }
        ) {
            Text(
                stringResource(id = R.string.forgot_password),
                color = BrickRed
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { navController.navigate(Screen.Register.route) }
        ) {
            Text(
                stringResource(id = R.string.not_registered),
                color = BrickRed
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    LoginContent(
        navController = navController,
        onLogin = { _, _ -> },
        showSnackBar = { },
        onForgotPassword = { })
}
