package com.devpaul.infoxperu.feature.user_start.login.data.datasource.mapper

import com.devpaul.infoxperu.feature.user_start.login.data.datasource.dto.response.ResponseLogin
import com.devpaul.infoxperu.feature.user_start.login.domain.entity.LoginE

class LoginMapper {

    fun mapResponseToEntity(responseLogin: ResponseLogin): LoginE {
        return LoginE(
            status = responseLogin.status,
            message = responseLogin.message,
            uid = responseLogin.uid // Aquí se maneja el campo que es nullable
        )
    }
}
