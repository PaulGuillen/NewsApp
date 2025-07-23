package com.devpaul.profile.data.datasource.mapper

import com.devpaul.profile.data.datasource.dto.res.IncrementLikeResponse
import com.devpaul.profile.domain.entity.GenericEntity

fun IncrementLikeResponse.toDomain() : GenericEntity {
    return GenericEntity(
        status = this.status,
        message = this.message,
    )
}