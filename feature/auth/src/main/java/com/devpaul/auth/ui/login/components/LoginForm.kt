package com.devpaul.auth.ui.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.devpaul.shared.ui.components.atoms.base.CustomButton
import com.devpaul.shared.ui.components.atoms.base.EmailField
import com.devpaul.shared.ui.components.atoms.base.PasswordField
import com.devpaul.shared.ui.components.atoms.base.ScreenLoading
import com.devpaul.shared.ui.components.organisms.BaseContentLayout

@Composable
fun LoginForm(
    navHostController: NavHostController,
    onLogin: (String, String) -> Unit,
    onForgotPassword: (String) -> Unit,
    showSnackBar: (String) -> Unit,
    uiState: ResultState<Login>?,
    onSuccess: @Composable () -> Unit?,
    onError: () -> Unit,
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
        header = {},
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

        is ResultState.Success -> {
            onSuccess()
        }

        is ResultState.Error -> {
            onError()
        }

        else -> {
            //
        }
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
    onForgotPasswordClick: () -> Unit
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
                EmailField(value = email, onValueChange = onEmailChange)

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

        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onForgotPasswordClick
        ) {
            Text(stringResource(id = R.string.forgot_password), color = BrickRed)
        }

        Spacer(modifier = Modifier.height(10.dp))

        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.login_button),
            onClick = onLoginClick,
        )
    }
}

@Composable
fun LoginFooter(
    navHostController: NavHostController
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.not_have_account),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
        TextButton(onClick = {
            navHostController.navigate(Screen.Register.route)
        }) {
            Text(
                text = stringResource(id = R.string.register),
                color = BrickRed,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
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
                onForgotPasswordClick = {}
            )
        },
        footer = {
            LoginFooter(navHostController = rememberNavController())
        }
    )
}