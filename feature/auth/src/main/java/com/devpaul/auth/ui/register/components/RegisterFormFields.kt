package com.devpaul.auth.ui.register.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.R
import com.devpaul.shared.ui.components.atoms.RegisterFormCallbacks
import com.devpaul.shared.ui.components.atoms.RegisterFormState
import com.devpaul.shared.ui.components.atoms.PasswordField
import com.devpaul.shared.ui.components.atoms.CustomOutlinedTextField

@Composable
fun RegisterFormFields(
    state: RegisterFormState,
    callbacks: RegisterFormCallbacks,
) {
    CustomOutlinedTextField(
        value = state.name,
        onValueChange = callbacks.onNameChange,
        labelRes = R.string.register_screen_name,
        leadingIcon = Icons.Default.Person,
    )

    Spacer(modifier = Modifier.height(8.dp))

    CustomOutlinedTextField(
        value = state.lastName,
        onValueChange = callbacks.onLastNameChange,
        labelRes = R.string.register_screen_last_name,
        leadingIcon = Icons.Default.Person,
    )

    Spacer(modifier = Modifier.height(8.dp))

    CustomOutlinedTextField(
        value = state.email,
        onValueChange = callbacks.onEmailChange,
        labelRes = R.string.register_screen_email,
        leadingIcon = Icons.Default.Email,
    )

    Spacer(modifier = Modifier.height(8.dp))

    PasswordField(
        value = state.password,
        onValueChange = callbacks.onPasswordChange,
        label = stringResource(id = R.string.register_screen_password),
        passwordVisible = state.passwordVisible,
        onPasswordVisibilityChange = callbacks.onPasswordVisibilityChange,
    )

    Spacer(modifier = Modifier.height(8.dp))

    PasswordField(
        value = state.confirmPassword,
        onValueChange = callbacks.onConfirmPasswordChange,
        label = stringResource(id = R.string.register_screen_confirm_password),
        passwordVisible = state.confirmPasswordVisible,
        onPasswordVisibilityChange = callbacks.onConfirmPasswordVisibilityChange,
    )
}

@Preview(showBackground = true, name = "Register Form Fields Preview")
@Composable
fun PreviewRegisterFormFields() {
    val state = RegisterFormState(
        name = "Paul",
        lastName = "Guillen",
        email = "correo@ejemplo.com",
        passwordVisible = false,
        confirmPasswordVisible = false,
    )

    val callbacks = RegisterFormCallbacks(
        onNameChange = {},
        onLastNameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onPasswordVisibilityChange = {},
        onConfirmPasswordVisibilityChange = {},
    )

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            RegisterFormFields(state = state, callbacks = callbacks)
        }
    }
}