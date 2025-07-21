package com.devpaul.profile.data.datasource.mapper

import com.devpaul.profile.data.datasource.dto.res.IncrementLikeResponse
import com.devpaul.profile.domain.entity.IncrementLikeEntity

fun IncrementLikeResponse.toDomain() : IncrementLikeEntity {
    return IncrementLikeEntity(
        status = status,
        message = message,
    )
}