package com.devpaul.home.data.datasource.mapper

import com.devpaul.home.data.datasource.dto.response.UITResponse
import com.devpaul.home.domain.entity.UITEntity

fun UITResponse.toDomain(): UITEntity {
    return UITEntity(
        service = service,
        site = site,
        link = link,
        year = year,
        value = value,
    )
}