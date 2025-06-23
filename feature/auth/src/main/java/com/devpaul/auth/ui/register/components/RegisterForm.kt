package com.devpaul.auth.ui.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.auth.ui.register.RegisterFormCallbacks
import com.devpaul.auth.ui.register.RegisterFormState
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.validateRegistration
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.White
import com.devpaul.shared.ui.components.atoms.base.CustomButton

@Composable
fun RegisterForm(
    navHostController: NavHostController,
    onRegister: (String, String, String, String, String, String) -> Unit,
    showSnackBar: (String) -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    fun validateAndRegister() {
        val validationResult = validateRegistration(
            context = context,
            name = name,
            lastName = lastName,
            phone = phone,
            email = email,
            birthdate = birthdate,
            password = password,
            confirmPassword = confirmPassword
        )
        if (validationResult != null) {
            showSnackBar(validationResult)
        } else {
            onRegister(name, lastName, phone, birthdate, email, password)
        }
    }

    val state = RegisterFormState(
        name = name,
        lastName = lastName,
        phone = phone,
        email = email,
        birthdate = birthdate,
        password = password,
        confirmPassword = confirmPassword,
        passwordVisible = passwordVisible,
        confirmPasswordVisible = confirmPasswordVisible
    )

    val callbacks = RegisterFormCallbacks(
        onNameChange = { name = it },
        onLastNameChange = { lastName = it },
        onPhoneChange = { phone = it },
        onEmailChange = { email = it },
        onBirthdateChange = { birthdate = it },
        onPasswordChange = { password = it },
        onConfirmPasswordChange = { confirmPassword = it },
        onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
        onConfirmPasswordVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(id = R.string.register_screen_description),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                RegisterFormFields(state = state, callbacks = callbacks)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.register_screen_register_button),
            onClick = { validateAndRegister() },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = stringResource(id = R.string.register_screen_already_have_account),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            TextButton(onClick = {
                navHostController.popBackStack()
            }) {
                Text(
                    stringResource(id = R.string.login_button),
                    color = BrickRed
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterFormPreview() {
    RegisterForm(
        navHostController = rememberNavController(),
        onRegister = { _, _, _, _, _, _ -> },
        showSnackBar = {}
    )
}