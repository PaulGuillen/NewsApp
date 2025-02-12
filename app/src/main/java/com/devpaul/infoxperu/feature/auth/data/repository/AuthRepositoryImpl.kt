package com.devpaul.infoxperu.feature.auth.data.repository

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.feature.auth.data.datasource.ds.AuthServiceDS
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestLogin
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRecoveryPassword
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRegister
import com.devpaul.infoxperu.feature.auth.domain.entity.LoginE
import com.devpaul.infoxperu.feature.auth.domain.entity.RecoveryPasswordE
import com.devpaul.infoxperu.feature.auth.domain.entity.RegisterE
import com.devpaul.infoxperu.feature.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val serviceDS: AuthServiceDS
) : AuthRepository {

    override suspend fun login(request: RequestLogin): LoginE {
        return serviceDS.login(
            RequestLogin(
                email = request.email,
                type = request.type,
                googleToken = request.googleToken,
            )
        )
    }

    override suspend fun register(request: RequestRegister): RegisterE {
        return serviceDS.register(
            RequestRegister(
                name = request.name,
                lastname = request.lastname,
                email = request.email,
                password = request.password,
            )
        )
    }

    override suspend fun recoveryPassword(request: RequestRecoveryPassword): RecoveryPasswordE {
        return serviceDS.recoveryPassword(
            RequestRecoveryPassword(
                email = request.email,
            )
        )
    }
}