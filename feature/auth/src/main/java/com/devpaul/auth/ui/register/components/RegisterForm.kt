package com.devpaul.auth.ui.register.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.auth.domain.entity.Register
import com.devpaul.auth.ui.register.RegisterFormCallbacks
import com.devpaul.auth.ui.register.RegisterFormState
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.extension.validateRegistration
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.White
import com.devpaul.shared.ui.components.atoms.base.CustomButton
import com.devpaul.shared.ui.components.atoms.base.ScreenLoading
import com.devpaul.shared.ui.components.atoms.base.dialog.ErrorNotification
import com.devpaul.shared.ui.components.atoms.base.dialog.SuccessNotification
import com.devpaul.shared.ui.components.organisms.BaseContentLayout

@Composable
fun RegisterForm(
    navHostController: NavHostController,
    onRegister: (String, String, String, String) -> Unit,
    showSnackBar: (String) -> Unit,
    registerState: ResultState<Register>?,
    onSuccess: () -> Unit,
    onError: () -> Unit,
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var showErrorDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(registerState) {
        when (registerState) {
            is ResultState.Error -> showErrorDialog = true
            is ResultState.Success -> showSuccessDialog = true
            else -> {
                showErrorDialog = false
                showSuccessDialog = false
            }
        }
    }

    fun validateAndRegister() {
        val validationResult = validateRegistration(
            context = context,
            name = name,
            lastName = lastName,
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )
        if (validationResult != null) {
            showSnackBar(validationResult)
        } else {
            onRegister(name, lastName, email, password)
        }
    }

    val state = RegisterFormState(
        name = name,
        lastName = lastName,
        email = email,
        password = password,
        confirmPassword = confirmPassword,
        passwordVisible = passwordVisible,
        confirmPasswordVisible = confirmPasswordVisible
    )

    val callbacks = RegisterFormCallbacks(
        onNameChange = { name = it },
        onLastNameChange = { lastName = it },
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onConfirmPasswordChange = { confirmPassword = it },
        onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
        onConfirmPasswordVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible }
    )

    BaseContentLayout(
        header = {},
        body = {
            RegisterBody(
                state = state,
                callbacks = callbacks,
            )
        },
        footer = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RegisterFooter(
                    navHostController = navHostController,
                    validateAndRegister = ::validateAndRegister,
                )
            }
        }
    )

    if (registerState is ResultState.Loading) {
        ScreenLoading()
    }

    if (registerState is ResultState.Success && showSuccessDialog) {
        SuccessNotification(
            visible = true,
            titleHeader = "¡Felicidades!",
            title = "Registro exitoso",
            message = "Tu cuenta ha sido creada exitosamente.",
            primaryButtonText = "Ir al inicio",
            onPrimaryClick = {
                showSuccessDialog = false
                onSuccess()
            },
            showDismissIcon = false
        )
    }

    if (registerState is ResultState.Error && showErrorDialog) {
        ErrorNotification(
            visible = true,
            titleHeader = "Oops!",
            title = "Error de registro",
            message = registerState.message,
            primaryButtonText = "Reintentar",
            onPrimaryClick = {
                showErrorDialog = false
                onError()
            },
            onDismiss = {
                showErrorDialog = false
            },
            showDismissIcon = true
        )
    }
}

@Composable
fun RegisterBody(
    state: RegisterFormState,
    callbacks: RegisterFormCallbacks,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_register),
            contentDescription = "Registro",
            modifier = Modifier
                .size(160.dp)
                .offset { IntOffset(0, 2) }
                .zIndex(1f)
        )
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
    }
}

@Composable
fun RegisterFooter(
    navHostController: NavHostController,
    validateAndRegister: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
    ) {
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.register_button),
            onClick = { validateAndRegister() },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.register_screen_already_have_account),
                style = MaterialTheme.typography.bodyMedium
            )
            TextButton(onClick = {
                navHostController.popBackStack()
            }) {
                Text(
                    text = stringResource(id = R.string.login_button),
                    color = BrickRed
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun BaseContentLayoutPreview() {
    BaseContentLayout(
        header = { },
        body = {
            RegisterBody(
                state = RegisterFormState(),
                callbacks = RegisterFormCallbacks(
                    onNameChange = { },
                    onLastNameChange = { },
                    onEmailChange = { },
                    onPasswordChange = { },
                    onConfirmPasswordChange = { },
                    onPasswordVisibilityChange = { },
                    onConfirmPasswordVisibilityChange = { }
                ),
            )
        },
        footer = {
            RegisterFooter(navHostController = rememberNavController(), validateAndRegister = { })
        }
    )
}