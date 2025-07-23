package com.devpaul.profile.data.datasource.mapper

import com.devpaul.profile.data.datasource.dto.res.UpdateResponse
import com.devpaul.profile.domain.entity.GenericEntity

fun UpdateResponse.toDomain(): GenericEntity {
    return GenericEntity(
        status = status,
        message = message
    )
}