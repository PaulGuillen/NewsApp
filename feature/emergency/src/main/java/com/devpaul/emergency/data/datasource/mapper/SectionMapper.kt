package com.devpaul.emergency.data.datasource.mapper

import com.devpaul.emergency.data.datasource.dto.res.SectionItemResponse
import com.devpaul.emergency.data.datasource.dto.res.SectionResponse
import com.devpaul.emergency.domain.entity.SectionEntity
import com.devpaul.emergency.domain.entity.SectionItemEntity

fun SectionResponse.toDomain(): SectionEntity {
    return SectionEntity(
        status = status,
        data = data.map { it.toDomain() }
    )
}

fun SectionItemResponse.toDomain(): SectionItemEntity {
    return SectionItemEntity(
        title = title,
        image = image,
        type = type
    )
}