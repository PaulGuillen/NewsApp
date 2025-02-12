package com.devpaul.infoxperu.feature.auth.domain.repository

import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestLogin
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRecoveryPassword
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRegister
import com.devpaul.infoxperu.feature.auth.domain.entity.LoginE
import com.devpaul.infoxperu.feature.auth.domain.entity.RecoveryPasswordE
import com.devpaul.infoxperu.feature.auth.domain.entity.RegisterE

interface AuthRepository {

    suspend fun login(request: RequestLogin): LoginE

    suspend fun register(request: RequestRegister): RegisterE

    suspend fun recoveryPassword(request: RequestRecoveryPassword): RecoveryPasswordE
}