package com.devpaul.auth.ui.login

import com.devpaul.auth.domain.entity.Login

data class LoginUIState(
    val data: Login? = null,
    val isLoading: Boolean = false
)