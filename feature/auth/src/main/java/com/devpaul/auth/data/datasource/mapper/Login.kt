package com.devpaul.auth.data.datasource.mapper

import com.devpaul.auth.data.datasource.dto.login.LoginResponse
import com.devpaul.auth.domain.entity.Login

fun LoginResponse.toDomain(): Login {
    return Login(
        status = this.status,
        message = this.message,
        uid = this.uid,
    )
}

