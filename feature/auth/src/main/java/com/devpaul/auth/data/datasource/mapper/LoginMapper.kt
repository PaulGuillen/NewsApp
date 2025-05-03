package com.devpaul.auth.data.datasource.mapper

import com.devpaul.auth.data.datasource.dto.login.LoginResponse
import com.devpaul.auth.domain.entity.Login

class LoginMapper {

    fun mapResponseToEntity(responseLogin: LoginResponse): Login {
        return Login(
            status = responseLogin.status,
            message = responseLogin.message,
            uid = responseLogin.uid,
        )
    }
}