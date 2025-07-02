package com.devpaul.profile.ui.update

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devpaul.core_data.util.Constant
import com.devpaul.core_platform.R
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.White
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
import com.devpaul.shared.ui.components.molecules.TopBar
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import com.devpaul.shared.ui.components.organisms.OnValueChangeEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdateScreen(
    navController: NavHostController,
) {
    val viewModel: UpdateViewModel = koinViewModel()

    val showDialogInfo = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }
    val showSuccessDialog = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    BaseScreenWithState(
        viewModel = viewModel,
        onInit = { _, _ -> viewModel.getProfileData() },
        navController = navController,
    ) { _, uiState, onIntent, _, _ ->

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
                },
                onDismiss = {
                    showSuccessDialog.value = false
                    viewModel.resetUpdateUser()
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
                onDismiss = {
                    showErrorDialog.value = false
                    viewModel.resetUpdateUser()
                },
                showDismissIcon = false
            )
        }

        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.header_update),
                    onBackClick = { navController.popBackStack() },
                )
            },
        ) { innerPadding ->
            UpdateScreenContent(
                viewModel = viewModel,
                innerPadding = innerPadding,
                uiState = uiState,
                onIntent = onIntent,
                onShowInfoDialog = { showDialogInfo.value = true }
            )
        }
    }
}


@Composable
fun UpdateScreenContent(
    viewModel: UpdateViewModel?,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    uiState: UpdateUiState,
    onIntent: (UpdateUiIntent) -> Unit,
    onShowInfoDialog: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val base64Image = remember { mutableStateOf<String?>(null) }

    OnValueChangeEffect(uiState.profile) { profile ->
        name = profile.name
        lastname = profile.lastname
        phone = profile.phone
        birthdate = profile.birthdate
        email = profile.email
        password = profile.password
        base64Image.value = profile.image
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ProfileImagePicker(
                defaultImageUrl = Constant.URL_IMAGE,
                base64Image = base64Image.value,
                showDialogOnClick = true,
                onImageSelected = { base64, uri ->
                    base64Image.value = base64
                    imageUri.value = uri
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    CustomOutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        labelRes = R.string.register_screen_name,
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomOutlinedTextField(
                        value = lastname,
                        onValueChange = { lastname = it },
                        labelRes = R.string.register_screen_last_name,
                        leadingIcon = Icons.Default.Person
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PhoneOutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        labelRes = R.string.register_screen_phone,
                        maxLength = 9,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DateTextField(
                        value = birthdate,
                        onDateSelected = { birthdate = it },
                        labelRes = R.string.register_screen_birthdate,
                        enabled = true,
                        tooltipMessage = "Selecciona el icono para elegir la fecha",
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomOutlinedTextFieldWithTooltip(
                        value = email,
                        onValueChange = { email = it },
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
                        value = password,
                        onValueChange = { password = it },
                        label = stringResource(id = R.string.register_screen_password),
                        passwordVisible = isPasswordVisible,
                        onPasswordVisibilityChange = { isPasswordVisible = !isPasswordVisible },
                        enabled = false,
                        tooltipMessage = "No puedes modificar este campo",
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.button_update),
                onClick = {
                    val updatedProfile = ProfileUserEntity(
                        id = uiState.profile?.id.orEmpty(),
                        uid = uiState.profile?.uid.orEmpty(),
                        name = name,
                        lastname = lastname,
                        birthdate = birthdate,
                        phone = phone,
                        email = email,
                        password = password,
                        image = base64Image.value
                    )

                    if (viewModel?.hasProfileChanged(updatedProfile) == true) {
                        onIntent(UpdateUiIntent.UpdateProfile(updatedProfile))
                    } else {
                        onShowInfoDialog()
                    }
                },
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileUpdateScreenPreview() {
    UpdateScreenContent(
        viewModel = null,
        innerPadding = PaddingValues(0.dp),
        uiState = UpdateUiState(),
        onIntent = {},
        onShowInfoDialog = {}
    )
}