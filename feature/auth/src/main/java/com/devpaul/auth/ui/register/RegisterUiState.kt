package com.devpaul.auth.ui.register

import com.devpaul.auth.domain.entity.Register
import com.devpaul.core_platform.extension.ResultState

data class RegisterUiState(
    val register: ResultState<Register>? = null,
)