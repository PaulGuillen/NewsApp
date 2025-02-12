package com.devpaul.infoxperu.feature.auth.data.datasource.mapper

import com.devpaul.infoxperu.feature.auth.data.datasource.dto.response.ResponseRecoveryPassword
import com.devpaul.infoxperu.feature.auth.domain.entity.RecoveryPasswordE

class RecoveryPasswordMapper {

    fun mapResponseToEntity(responseRecoveryPassword: ResponseRecoveryPassword): RecoveryPasswordE {
        return RecoveryPasswordE(
            status = responseRecoveryPassword.status,
            message = responseRecoveryPassword.message,
        )
    }
}