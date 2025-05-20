package com.devpaul.auth.data.datasource.mapper

import com.devpaul.auth.data.datasource.dto.register.RegisterResponse
import com.devpaul.auth.domain.entity.Register

fun RegisterResponse.toDomain(): Register {
    return Register(
        status = this.status,
        message = this.message,
        uid = this.uid,
    )
}