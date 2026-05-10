package com.devpaul.auth.ui.register.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.auth.domain.entity.Register
import com.devpaul.auth.ui.components.rememberAuthUiColors
import com.devpaul.auth.ui.register.RegisterFormCallbacks
import com.devpaul.auth.ui.register.RegisterFormState
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.extension.validateRegistration
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.shared.ui.components.atoms.base.ScreenLoading
import com.devpaul.shared.ui.components.atoms.base.button.CustomButton
import com.devpaul.shared.ui.components.atoms.base.dialog.ErrorNotification
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
    LaunchedEffect(registerState) {
        when (registerState) {
            is ResultState.Error -> showErrorDialog = true
            is ResultState.Success -> {
                onSuccess()
            }
            else -> {
                showErrorDialog = false
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
    val colors = rememberAuthUiColors()
    val cardShape = RoundedCornerShape(28.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .imePadding()
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-90).dp, y = (-20).dp)
                .background(colors.accentSoft, CircleShape)
        )

        Box(
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 40.dp, y = 30.dp)
                .background(colors.accentSoft.copy(alpha = 0.65f), CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Crear Cuenta",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = colors.title
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Regístrate para acceder a las últimas noticias financieras y herramientas de inversión.",
                style = MaterialTheme.typography.bodyLarge,
                color = colors.body
            )

            Spacer(modifier = Modifier.height(22.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 16.dp,
                        shape = cardShape,
                        ambientColor = colors.buttonGlow,
                        spotColor = colors.buttonGlow
                    ),
                colors = CardDefaults.cardColors(containerColor = colors.card),
                shape = cardShape,
                border = BorderStroke(1.dp, colors.cardBorder),
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 22.dp)) {
                    Text(
                        text = stringResource(id = R.string.register_screen_description),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                        color = colors.body,
                        modifier = Modifier.padding(bottom = 18.dp),
                    )

                    RegisterFormFields(state = state, callbacks = callbacks)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.register_button),
                onClick = { validateAndRegister() },
                containerColor = colors.buttonContainer,
                contentColor = colors.buttonContent,
                glowColor = colors.buttonGlow,
                shadowEnabled = true,
                borderColor = colors.buttonBorder
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Al registrarte, aceptas nuestros Términos y Condiciones y la Política de Privacidad.",
                style = MaterialTheme.typography.bodySmall,
                color = colors.body,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun RegisterFooter(
    navHostController: NavHostController,
) {
    val colors = rememberAuthUiColors()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.backgroundSolid)
            .padding(bottom = 8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.register_screen_already_have_account),
                style = MaterialTheme.typography.bodyMedium,
                color = colors.body
            )
            TextButton(onClick = {
                navHostController.navigate(Screen.Login.route) {
                    launchSingleTop = true
                }
            }) {
                Text(
                    text = stringResource(id = R.string.login_button),
                    color = colors.link
                )
            }
        }
    }
}

@Preview(
    name = "Register Form - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Register Form - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BaseContentLayoutPreview() {
    InfoXPeruTheme(darkTheme = androidx.compose.foundation.isSystemInDarkTheme(), dynamicColor = false) {
        BaseContentLayout(
            body = {
                RegisterBody(
                    state = RegisterFormState(),
                    callbacks = RegisterFormCallbacks(
                        onNameChange = {},
                        onLastNameChange = {},
                        onEmailChange = {},
                        onPasswordChange = {},
                        onConfirmPasswordChange = {},
                        onPasswordVisibilityChange = {},
                        onConfirmPasswordVisibilityChange = {}
                    ),
                    validateAndRegister = {}
                )
            },
            footer = {
                RegisterFooter(navHostController = rememberNavController())
            }
        )
    }
}