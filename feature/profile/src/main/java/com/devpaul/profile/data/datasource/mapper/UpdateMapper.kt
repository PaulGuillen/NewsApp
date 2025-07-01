package com.devpaul.profile.data.datasource.mapper

import com.devpaul.profile.data.datasource.dto.res.UpdateResponse
import com.devpaul.profile.domain.entity.UpdateEntity

fun UpdateResponse.toDomain(): UpdateEntity {
    return UpdateEntity(
        status = status,
        message = message
    )
}