package com.devpaul.home.data.datasource.mapper

import com.devpaul.home.data.datasource.dto.response.HolidayAlertResponse
import com.devpaul.home.domain.entity.HolidayAlertEntity

fun HolidayAlertResponse.toDomain(): HolidayAlertEntity {
    return HolidayAlertEntity(
        service = service,
        source = source,
        link = link,
        date = date,
        alert = alert,
        status = status,
        title = title,
        scope = scope,
        data = data,
    )
}