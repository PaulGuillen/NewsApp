package com.devpaul.home.data.datasource.mapper

import com.devpaul.home.data.datasource.dto.response.UITData
import com.devpaul.home.data.datasource.dto.response.UITResponse
import com.devpaul.home.domain.entity.UITDataEntity
import com.devpaul.home.domain.entity.UITEntity

fun UITResponse.toDomain(): UITEntity {
    return UITEntity(
        status = status,
        message = message,
        data = data.toDomain()
    )
}

fun UITData.toDomain(): UITDataEntity {
    return UITDataEntity(
        service = service,
        site = site,
        link = link,
        year = year,
        value = value
    )
}