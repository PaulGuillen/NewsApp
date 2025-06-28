package com.devpaul.profile.data.datasource.mapper

import com.devpaul.profile.data.datasource.dto.res.ProfileResponse
import com.devpaul.profile.data.datasource.dto.res.ProfileUserResponse
import com.devpaul.profile.domain.entity.ProfileEntity
import com.devpaul.profile.domain.entity.ProfileUserEntity

fun ProfileResponse.toDomain(): ProfileEntity {
    return ProfileEntity(
        status = status,
        message = message,
        data = data.toDomain()
    )
}

fun ProfileUserResponse.toDomain(): ProfileUserEntity {
    return ProfileUserEntity(
        id = id,
        uid = uid,
        name = name,
        lastname = lastname,
        phone = phone,
        birthdate = birthdate,
        email = email,
        password = password,
    )
}