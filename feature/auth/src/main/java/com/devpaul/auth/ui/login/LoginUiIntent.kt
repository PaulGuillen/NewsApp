package com.devpaul.auth.ui.login

sealed class LoginUiIntent {
    data class Login(val email: String, val password: String? = "") : LoginUiIntent()
    data class ResetPassword(val email: String) : LoginUiIntent()
    data object CheckUserLoggedIn : LoginUiIntent()
}