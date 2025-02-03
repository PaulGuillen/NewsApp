package com.devpaul.infoxperu.feature.user_start.login.domain.repository

import com.devpaul.infoxperu.feature.user_start.login.data.datasource.dto.request.RequestLogin
import com.devpaul.infoxperu.feature.user_start.login.domain.entity.LoginE

interface LoginRepository {

    suspend fun login(request: RequestLogin): LoginE

}