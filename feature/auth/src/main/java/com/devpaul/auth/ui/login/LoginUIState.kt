package com.devpaul.auth.ui.login

import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.domain.entity.Login
import com.devpaul.core_platform.extension.ResultState

data class LoginUIState(
    val login: ResultState<Login>? = null,
    val loginRequest: LoginRequest? = null,
)