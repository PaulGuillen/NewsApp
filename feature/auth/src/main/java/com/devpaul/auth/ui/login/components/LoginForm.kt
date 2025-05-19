package com.devpaul.auth.ui.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.validateEmail
import com.devpaul.core_platform.extension.validateStartSession
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.White
import com.devpaul.shared.components.molecules.EmailField
import com.devpaul.shared.components.molecules.PasswordField

@Composable
fun LoginForm(
    onLoginClick: (String, String) -> Unit,
    onForgotPasswordClick: (String) -> Unit,
    onRegisterClick: () -> Unit,
    showSnackBar: (String) -> Unit,
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
            onLoginClick(email, password)
        }
    }

    fun validateRecoveryPassword() {
        val validationResult = validateEmail(context, email)
        if (validationResult != null) {
            showSnackBar(validationResult)
        } else {
            onForgotPasswordClick(email)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_info_peru),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.size(280.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        EmailField(value = email, onValueChange = { email = it })

        Spacer(modifier = Modifier.height(8.dp))

        PasswordField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = { passwordVisible = !passwordVisible }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { validateLogin() },
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

        TextButton(onClick = { validateRecoveryPassword() }) {
            Text(stringResource(id = R.string.forgot_password), color = BrickRed)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { onRegisterClick() }) {
            Text(stringResource(id = R.string.not_registered), color = BrickRed)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginFormPreview() {
    LoginForm(
        onLoginClick = { _, _ -> },
        onForgotPasswordClick = { _ -> },
        onRegisterClick = { },
        showSnackBar = { _ -> },
    )
}