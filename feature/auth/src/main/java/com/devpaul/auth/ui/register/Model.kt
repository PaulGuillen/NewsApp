package com.devpaul.auth.ui.register

data class RegisterFormState(
    val name: String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    val birthdate: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false,
)

data class RegisterFormCallbacks(
    val onNameChange: (String) -> Unit,
    val onLastNameChange: (String) -> Unit,
    val onPhoneChange: (String) -> Unit,
    val onEmailChange: (String) -> Unit,
    val onBirthdateChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val onConfirmPasswordChange: (String) -> Unit,
    val onPasswordVisibilityChange: () -> Unit,
    val onConfirmPasswordVisibilityChange: () -> Unit,
)