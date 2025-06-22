package com.devpaul.auth.ui.register

sealed class RegisterUiIntent {
    data class Register(
        val name: String,
        val lastname: String,
        val phone: String,
        val birthdate: String,
        val email: String,
        val password: String,
    ) : RegisterUiIntent()
}