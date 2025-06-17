package com.devpaul.home.data.datasource.mapper

import com.devpaul.home.data.datasource.dto.response.SectionItem
import com.devpaul.home.data.datasource.dto.response.SectionResponse
import com.devpaul.home.domain.entity.SectionDataEntity
import com.devpaul.home.domain.entity.SectionEntity

fun SectionResponse.toDomain(): SectionEntity {
    return SectionEntity(
        status = status,
        message = message,
        data = data.map { it.toDomain() }
    )
}

fun SectionItem.toDomain(): SectionDataEntity {
    return SectionDataEntity(
        id = id,
        title = title,
        type = type,
        imageUrl = imageUrl,
        description = description,
        destination = destination
    )
}
