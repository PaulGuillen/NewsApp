package com.devpaul.infoxperu.feature.auth.data.repository

import com.devpaul.infoxperu.feature.auth.data.datasource.ds.AuthServiceDS
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestLogin
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRegister
import com.devpaul.infoxperu.feature.auth.domain.entity.LoginE
import com.devpaul.infoxperu.feature.auth.domain.entity.RegisterE
import com.devpaul.infoxperu.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val serviceDS: AuthServiceDS
) : AuthRepository {

    override suspend fun login(requestLogin: RequestLogin): LoginE {
        val request = RequestLogin(
            email = requestLogin.email,
            type = requestLogin.type,
            googleToken = requestLogin.googleToken,
        )
        return serviceDS.login(request)
    }

    override suspend fun register(requestLogin: RequestRegister): RegisterE {
        val request = RequestRegister(
            userUID = requestLogin.userUID,
            name = requestLogin.name,
            lastName = requestLogin.lastName,
            email = requestLogin.email,
            password = requestLogin.password,
        )
        return serviceDS.register(request)
    }
}