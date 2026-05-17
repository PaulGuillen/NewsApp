package com.devpaul.auth.ui.login.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.shadow
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
import com.devpaul.auth.ui.components.rememberAuthUiColors
import com.devpaul.auth.ui.login.LoginUIState
import com.devpaul.auth.ui.login.LoginUiIntent
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.extension.validateEmail
import com.devpaul.core_platform.extension.validateStartSession
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.core_platform.theme.White
import com.devpaul.shared.ui.components.atoms.base.ScreenLoading
import com.devpaul.shared.ui.components.atoms.base.button.CustomButton
import com.devpaul.shared.ui.components.atoms.base.textfield.CustomOutlinedTextField
import com.devpaul.shared.ui.components.atoms.base.textfield.PasswordField
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.DialogCard

@Composable
fun LoginForm(
    navHostController: NavHostController,
    onLogin: (String, String, Boolean) -> Unit,
    onForgotPassword: (String) -> Unit,
    showSnackBar: (String) -> Unit,
    onIntent: (LoginUiIntent) -> Unit,
    uiState: LoginUIState?,
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
            onLogin(email, password, rememberMe)
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
        isBodyScrollable = true,
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

    when (val status = uiState?.loginStatus) {
        is ResultState.Loading -> ScreenLoading()
        is ResultState.Error -> {
            DialogCard(
                message = status.message,
                onDismiss = {
                    onIntent(LoginUiIntent.DismissDialog)
                }
            )
        }

        else -> Unit
    }

    when (uiState?.recoveryPasswordStatus) {
        is ResultState.Loading -> ScreenLoading()
        else -> Unit
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
    val colors = rememberAuthUiColors()
    val cardShape = RoundedCornerShape(28.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Box(
            modifier = Modifier
                .size(220.dp)
                .offset(x = (-70).dp, y = (-30).dp)
                .background(colors.accentSoft, CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 18.dp,
                        shape = RoundedCornerShape(34.dp),
                        ambientColor = colors.buttonGlow,
                        spotColor = colors.buttonGlow
                    )
                    .background(
                        color = colors.accentSoft,
                        shape = RoundedCornerShape(34.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_info_peru),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier.size(260.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colors.card),
                shape = cardShape,
                border = BorderStroke(1.dp, colors.cardBorder),
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Bienvenido a Info Perú",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        color = colors.title,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Ingresa con tus credenciales para iniciar sesión",
                        fontSize = 15.sp,
                        color = colors.body,
                        textAlign = TextAlign.Center,
                    )

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = colors.cardBorder.copy(alpha = 0.65f),
                        modifier = Modifier.padding(vertical = 14.dp)
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

                    Spacer(modifier = Modifier.height(10.dp))

                    PasswordField(
                        value = password,
                        onValueChange = onPasswordChange,
                        label = "Contraseña",
                        passwordVisible = passwordVisible,
                        onPasswordVisibilityChange = onPasswordVisibilityChange
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

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
                        checkedColor = colors.accent,
                        uncheckedColor = colors.checkboxUnchecked,
                        checkmarkColor = White
                    )
                )
                Text(
                    modifier = Modifier.offset(x = (-10).dp),
                    text = "Recordar usuario/contraseña",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 14.sp,
                    color = colors.body,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.login_button),
                onClick = onLoginClick,
                painterIcon = painterResource(id = R.drawable.baseline_self_improvement_24),
                containerColor = colors.buttonContainer,
                contentColor = colors.buttonContent,
                glowColor = colors.buttonGlow,
                shadowEnabled = true,
                borderColor = colors.buttonBorder
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = onForgotPasswordClick
            ) {
                Text(
                    stringResource(id = R.string.forgot_password),
                    modifier = Modifier.offset(x = 10.dp),
                    color = colors.link,
                )
            }
        }
    }
}

@Composable
fun LoginFooter(
    navHostController: NavHostController
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.not_have_account),
                color = colors.body,
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
                    modifier = Modifier.offset(x = (-4).dp),
                    text = stringResource(id = R.string.register),
                    color = colors.link
                )
            }
        }
    }
}

@Preview(
    name = "Login Form - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Login Form - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BaseContentLayoutPreview() {
    InfoXPeruTheme(darkTheme = androidx.compose.foundation.isSystemInDarkTheme(), dynamicColor = false) {
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
}