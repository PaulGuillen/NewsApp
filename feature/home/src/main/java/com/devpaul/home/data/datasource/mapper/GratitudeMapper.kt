package com.devpaul.home.data.datasource.mapper

import com.devpaul.home.data.datasource.dto.response.GratitudeItem
import com.devpaul.home.data.datasource.dto.response.GratitudeResponse
import com.devpaul.home.domain.entity.GratitudeDataEntity
import com.devpaul.home.domain.entity.GratitudeEntity

fun GratitudeResponse.toDomain(): GratitudeEntity {
    return GratitudeEntity(
        status = status,
        message = message,
        data = data.map { it.toDomain() }
    )
}

fun GratitudeItem.toDomain(): GratitudeDataEntity {
    return GratitudeDataEntity(
        id = id,
        imageUrl = imageUrl,
        title = title,
        link = link
    )
}