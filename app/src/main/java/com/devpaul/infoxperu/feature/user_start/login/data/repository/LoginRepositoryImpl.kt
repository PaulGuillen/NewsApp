package com.devpaul.infoxperu.feature.user_start.login.data.repository

import com.devpaul.infoxperu.feature.user_start.login.data.datasource.ds.LoginServiceDS
import com.devpaul.infoxperu.feature.user_start.login.data.datasource.dto.request.RequestLogin
import com.devpaul.infoxperu.feature.user_start.login.domain.entity.LoginE
import com.devpaul.infoxperu.feature.user_start.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val serviceDS: LoginServiceDS
) : LoginRepository {

    override suspend fun login(requestLogin: RequestLogin): LoginE {
        val request = RequestLogin(
            email = requestLogin.email,
            type = requestLogin.type,
            googleToken = requestLogin.googleToken
        )

        return serviceDS.loginService(request)
    }
}