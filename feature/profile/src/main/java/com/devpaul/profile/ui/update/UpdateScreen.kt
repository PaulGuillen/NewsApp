package com.devpaul.profile.ui.update

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_data.util.Constant
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.domain.entity.ProfileUserEntity
import com.devpaul.shared.ui.components.atoms.base.ScreenLoading
import com.devpaul.shared.ui.components.atoms.base.button.CustomButton
import com.devpaul.shared.ui.components.atoms.base.dialog.ErrorNotification
import com.devpaul.shared.ui.components.atoms.base.dialog.InfoNotification
import com.devpaul.shared.ui.components.atoms.base.dialog.SuccessNotification
import com.devpaul.shared.ui.components.atoms.base.image.ProfileImagePicker
import com.devpaul.shared.ui.components.atoms.base.textfield.CustomOutlinedTextField
import com.devpaul.shared.ui.components.atoms.base.textfield.DateTextField
import com.devpaul.shared.ui.components.atoms.base.textfield.PhoneOutlinedTextField
import com.devpaul.shared.ui.components.atoms.base.tooltip.CustomOutlinedTextFieldWithTooltip
import com.devpaul.shared.ui.components.atoms.base.tooltip.PasswordFieldWithTooltip
import com.devpaul.shared.ui.components.molecules.TopBarPrincipal
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import com.devpaul.shared.ui.components.organisms.OnValueChangeEffect
import com.devpaul.shared.ui.components.organisms.setNavigationResult
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdateScreen(
    navController: NavHostController,
) {
    val viewModel: UpdateViewModel = koinViewModel()

    val name = remember { mutableStateOf("") }
    val lastname = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val birthdate = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val base64Image = remember { mutableStateOf<String?>(null) }
    val isPasswordVisible = remember { mutableStateOf(false) }

    val showDialogInfo = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }
    val showSuccessDialog = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    val shouldReloadGlobal = remember { mutableStateOf(false) }

    BaseScreenWithState(
        viewModel = viewModel,
        onInit = { _, _ -> viewModel.getProfileData() },
        navController = navController,
    ) { _, uiState, onIntent, _, _ ->
        UpdateScreenContent(
            name = name,
            lastname = lastname,
            phone = phone,
            birthdate = birthdate,
            email = email,
            password = password,
            isPasswordVisible = isPasswordVisible,
            base64Image = base64Image,
            imageUri = imageUri,
            uiState = uiState,
            viewModel = viewModel,
            showDialogInfo = showDialogInfo,
            showErrorDialog = showErrorDialog,
            showSuccessDialog = showSuccessDialog,
            errorMessage = errorMessage,
            onIntent = onIntent,
            shouldReloadGlobal = shouldReloadGlobal,
            navController = navController
        )
    }
}

@Composable
fun UpdateScreenContent(
    name: MutableState<String>,
    lastname: MutableState<String>,
    phone: MutableState<String>,
    birthdate: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    isPasswordVisible: MutableState<Boolean>,
    base64Image: MutableState<String?>,
    imageUri: MutableState<Uri?>,
    uiState: UpdateUiState,
    viewModel: UpdateViewModel,
    showDialogInfo: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    showSuccessDialog: MutableState<Boolean>,
    errorMessage: MutableState<String>,
    onIntent: (UpdateUiIntent) -> Unit,
    shouldReloadGlobal: MutableState<Boolean>,
    navController: NavHostController,
) {
    if (uiState.updateUser is ResultState.Loading) {
        ScreenLoading()
    }

    if (uiState.updateUser is ResultState.Success) {
        showSuccessDialog.value = true
    }

    if (uiState.updateUser is ResultState.Error) {
        showErrorDialog.value = true
        errorMessage.value = uiState.updateUser.message
    }

    if (showDialogInfo.value) {
        InfoNotification(
            visible = true,
            title = "Perfil sin cambios",
            message = "No se detectaron cambios en los campos del perfil.",
            primaryButtonText = "Aceptar",
            onPrimaryClick = { showDialogInfo.value = false },
            onDismiss = { showDialogInfo.value = false },
            showDismissIcon = false
        )
    }

    if (showSuccessDialog.value) {
        SuccessNotification(
            visible = true,
            titleHeader = "¡Felicidades!",
            title = "Actualización exitosa",
            message = "Los datos se han actualizado exitosamente.",
            primaryButtonText = "Aceptar",
            onPrimaryClick = {
                showSuccessDialog.value = false
                viewModel.resetUpdateUser()
                shouldReloadGlobal.value = true
                navController.setNavigationResult("shouldReload", true)
            },
            showDismissIcon = false
        )
    }

    if (showErrorDialog.value) {
        ErrorNotification(
            visible = true,
            titleHeader = "Error",
            title = "Actualización fallida",
            message = errorMessage.value.ifEmpty { "Ocurrió un error al actualizar los datos." },
            primaryButtonText = "Aceptar",
            onPrimaryClick = {
                showErrorDialog.value = false
                viewModel.resetUpdateUser()
            },
            showDismissIcon = false
        )
    }

    BaseContentLayout(
        isBodyScrollable = true,
        header = {
            UpdateScreenHeader(
                navController = navController,
                shouldReloadGlobal = shouldReloadGlobal
            )
        },
        body = {
            UpdateScreenBody(
                name = name,
                lastname = lastname,
                phone = phone,
                birthdate = birthdate,
                email = email,
                password = password,
                isPasswordVisible = isPasswordVisible,
                base64Image = base64Image,
                imageUri = imageUri,
                uiState = uiState,
            )

        },
        footer = {
            UpdateScreenFooter(
                name = name,
                lastname = lastname,
                phone = phone,
                birthdate = birthdate,
                email = email,
                password = password,
                base64Image = base64Image,
                uiState = uiState,
                viewModel = viewModel,
                showDialogInfo = showDialogInfo,
                onIntent = onIntent
            )
        }
    )
}

@Composable
fun UpdateScreenHeader(
    navController: NavHostController,
    shouldReloadGlobal: MutableState<Boolean>
) {
    TopBarPrincipal(
        style = 2,
        title = stringResource(R.string.header_update),
        onStartIconClick = {
            navController.setNavigationResult("shouldReload", shouldReloadGlobal.value)
            navController.popBackStack()
        }
    )
}

@Composable
fun UpdateScreenBody(
    name: MutableState<String>,
    lastname: MutableState<String>,
    phone: MutableState<String>,
    birthdate: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    isPasswordVisible: MutableState<Boolean>,
    base64Image: MutableState<String?>,
    imageUri: MutableState<Uri?>,
    uiState: UpdateUiState,
) {

    OnValueChangeEffect(uiState.profile) { profile ->
        name.value = profile.name
        lastname.value = profile.lastname
        phone.value = profile.phone
        birthdate.value = profile.birthdate
        email.value = profile.email
        password.value = profile.password
        base64Image.value = profile.image
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            ProfileImagePicker(
                defaultImageUrl = Constant.URL_IMAGE,
                base64Image = base64Image.value,
                showDialogOnClick = true,
                onImageSelected = { base64, uri ->
                    base64Image.value = base64
                    imageUri.value = uri
                },
                modifier = Modifier
                    .size(240.dp)
                    .align(Alignment.CenterHorizontally),
                isCircular = true,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    CustomOutlinedTextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        labelRes = R.string.register_screen_name,
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomOutlinedTextField(
                        value = lastname.value,
                        onValueChange = { lastname.value = it },
                        labelRes = R.string.register_screen_last_name,
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PhoneOutlinedTextField(
                        value = phone.value,
                        onValueChange = { phone.value = it },
                        labelRes = R.string.register_screen_phone,
                        maxLength = 9,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DateTextField(
                        value = birthdate.value,
                        onDateSelected = { birthdate.value = it },
                        labelRes = R.string.register_screen_birthdate,
                        enabled = true,
                        tooltipMessage = "Selecciona el icono para elegir la fecha",
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomOutlinedTextFieldWithTooltip(
                        value = email.value,
                        onValueChange = { email.value = it },
                        labelRes = R.string.register_screen_email,
                        leadingIcon = Icons.Default.Email,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        enabled = false,
                        tooltipMessage = "No puedes modificar este campo",
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PasswordFieldWithTooltip(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = stringResource(id = R.string.register_screen_password),
                        passwordVisible = isPasswordVisible.value,
                        onPasswordVisibilityChange = {
                            isPasswordVisible.value = !isPasswordVisible.value
                        },
                        enabled = false,
                        tooltipMessage = "No puedes modificar este campo",
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
fun UpdateScreenFooter(
    name: MutableState<String>,
    lastname: MutableState<String>,
    phone: MutableState<String>,
    birthdate: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    base64Image: MutableState<String?>,
    uiState: UpdateUiState,
    viewModel: UpdateViewModel?,
    showDialogInfo: MutableState<Boolean>,
    onIntent: (UpdateUiIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 10.dp)
    ) {
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.button_update),
            onClick = {
                val updatedProfile = ProfileUserEntity(
                    id = uiState.profile?.id.orEmpty(),
                    uid = uiState.profile?.uid.orEmpty(),
                    name = name.value,
                    lastname = lastname.value,
                    birthdate = birthdate.value,
                    phone = phone.value,
                    email = email.value,
                    password = password.value,
                    image = base64Image.value,
                )

                if (viewModel?.hasProfileChanged(updatedProfile) == true) {
                    onIntent(UpdateUiIntent.UpdateProfile(updatedProfile))
                } else {
                    showDialogInfo.value = true
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileUpdateScreenPreview() {
    BaseContentLayout(
        isBodyScrollable = true,
        header = {
            UpdateScreenHeader(
                navController = rememberNavController(),
                shouldReloadGlobal = remember { mutableStateOf(false) }
            )
        },
        body = {
            UpdateScreenBody(
                name = remember { mutableStateOf("Paul") },
                lastname = remember { mutableStateOf("Guillen") },
                phone = remember { mutableStateOf("977588530") },
                birthdate = remember { mutableStateOf("20/01/1998") },
                email = remember { mutableStateOf("paulguillen190@gmail.com") },
                password = remember { mutableStateOf("********") },
                isPasswordVisible = remember { mutableStateOf(false) },
                base64Image = remember { mutableStateOf(null) },
                imageUri = remember { mutableStateOf(null) },
                uiState = UpdateUiState(),
            )
        },
        footer = {
            UpdateScreenFooter(
                name = remember { mutableStateOf("Paul") },
                lastname = remember { mutableStateOf("Guillen") },
                phone = remember { mutableStateOf("977588530") },
                birthdate = remember { mutableStateOf("20/01/1998") },
                email = remember { mutableStateOf("paulguillen190@gmail.com") },
                password = remember { mutableStateOf("********") },
                base64Image = remember { mutableStateOf(null) },
                uiState = UpdateUiState(),
                viewModel = null,
                showDialogInfo = remember { mutableStateOf(false) },
                onIntent = {}
            )
        }
    )
}