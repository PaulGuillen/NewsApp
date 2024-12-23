package com.devpaul.infoxperu.feature.user_start.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.validateRegistration
import com.devpaul.infoxperu.domain.screen.BaseScreen
import com.devpaul.infoxperu.domain.screen.ShowDialogSuccessRegister
import com.devpaul.infoxperu.domain.ui.register_screen.RegisterFormCallbacks
import com.devpaul.infoxperu.domain.ui.register_screen.RegisterFormFields
import com.devpaul.infoxperu.domain.ui.register_screen.RegisterFormState
import com.devpaul.infoxperu.domain.ui.utils.ScreenLoading
import com.devpaul.infoxperu.feature.user_start.Screen
import com.devpaul.infoxperu.ui.theme.BrickRed
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiEvent by viewModel.uiEvent.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    BaseScreen { _, showSnackBar ->
        LaunchedEffect(uiEvent) {
            when (uiEvent) {
                is RegisterUiEvent.RegisterSuccess -> {
                    showDialog = true
                }

                is RegisterUiEvent.RegisterError -> {
                    showSnackBar((uiEvent as RegisterUiEvent.RegisterError).error)
                }

                else -> Unit
            }

            viewModel.resetUiEvent()
        }

        if (showDialog) {
            ShowDialogSuccessRegister {
                showDialog = false
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Register.route) { inclusive = true }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {

            if (isLoading) {
                ScreenLoading()
            }

            RegisterContent(
                navController = navController,
                onRegister = { name, lastName, email, password ->
                    viewModel.executeUiIntent(
                        RegisterUiIntent.Register(
                            name = name.trim(),
                            lastname = lastName.trim(),
                            email = email.trim(),
                            password = password.trim(),
                        )
                    )
                }, showSnackBar = { message ->
                    showSnackBar(message)
                })
        }
    }
}

@Composable
fun RegisterContent(
    navController: NavHostController,
    onRegister: (String, String, String, String) -> Unit,
    showSnackBar: (String) -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    fun validateAndRegister() {
        val validationResult = validateRegistration(
            context, name, lastName, email, password, confirmPassword
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { validateAndRegister() },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = RectangleShape,
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrickRed, contentColor = White)
                ) {
                    Text(stringResource(id = R.string.register_screen_register_button))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text(stringResource(id = R.string.register_screen_already_have_account), color = BrickRed)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    val navController = rememberNavController()
    RegisterContent(navController, onRegister = { _, _, _, _ -> }, showSnackBar = { })
}
