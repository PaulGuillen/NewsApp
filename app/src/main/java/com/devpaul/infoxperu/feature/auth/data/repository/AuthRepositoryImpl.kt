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

    override suspend fun login(login: RequestLogin): LoginE {
        val request = RequestLogin(
            email = login.email,
            type = login.type,
            googleToken = login.googleToken,
        )
        return serviceDS.login(request)
    }

    override suspend fun register(register: RequestRegister): RegisterE {
        val request = RequestRegister(
            name = register.name,
            lastname = register.lastname,
            email = register.email,
            password = register.password,
        )
        return serviceDS.register(request)
    }
}