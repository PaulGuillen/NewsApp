package com.devpaul.auth.ui.register.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.auth.domain.entity.Register
import com.devpaul.auth.ui.register.RegisterFormCallbacks
import com.devpaul.auth.ui.register.RegisterFormState
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.extension.validateRegistration
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.shared.ui.components.atoms.base.ScreenLoading
import com.devpaul.shared.ui.components.atoms.base.button.CustomButton
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
        isBodyScrollable = true,
        body = {
            RegisterBody(
                state = state,
                callbacks = callbacks,
                validateAndRegister = ::validateAndRegister
            )
        },
        footer = {
            RegisterFooter(
                navHostController = navHostController,
            )
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
    validateAndRegister: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.user_register),
            contentDescription = "Registro",
            modifier = Modifier
                .size(160.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
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
            text = stringResource(id = R.string.register_button),
            onClick = { validateAndRegister() },
        )

    }
}

@Composable
fun RegisterFooter(
    navHostController: NavHostController,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
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
                navHostController.navigate(Screen.Login.route) {
                    launchSingleTop = true
                }
            }) {
                Text(
                    text = stringResource(id = R.string.login_button),
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
                validateAndRegister = { }
            )
        },
        footer = {
            RegisterFooter(navHostController = rememberNavController())
        }
    )
}