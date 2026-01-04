package com.devpaul.auth.ui.login

import com.devpaul.auth.domain.entity.Auth
import com.devpaul.core_platform.extension.ResultState

data class LoginUIState(
    val loginStatus: ResultState<Auth> = ResultState.Idle,
    val recoveryPasswordStatus: ResultState<Unit> = ResultState.Idle,
)