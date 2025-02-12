package com.devpaul.infoxperu.feature.auth.data.datasource.mapper

import com.devpaul.infoxperu.feature.auth.data.datasource.dto.response.ResponseLogin
import com.devpaul.infoxperu.feature.auth.domain.entity.LoginE

class LoginMapper {

    fun mapResponseToEntity(responseLogin: ResponseLogin): LoginE {
        return LoginE(
            status = responseLogin.status,
            message = responseLogin.message,
            uid = responseLogin.uid
        )
    }
}