package com.devpaul.infoxperu.feature.auth.data.datasource.mapper

import com.devpaul.infoxperu.feature.auth.data.datasource.dto.response.ResponseRegister
import com.devpaul.infoxperu.feature.auth.domain.entity.RegisterE

class RegisterMapper {

    fun mapResponseToEntity(responseRegister: ResponseRegister): RegisterE {
        return RegisterE(
            status = responseRegister.status,
            message = responseRegister.message,
            uid = responseRegister.uid,
        )
    }
}