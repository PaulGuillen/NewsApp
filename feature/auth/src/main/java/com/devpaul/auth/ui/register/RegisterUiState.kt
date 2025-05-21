package com.devpaul.auth.ui.register

import com.devpaul.auth.domain.entity.Register

data class RegisterUiState(
    val data: Register? = null,
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
)