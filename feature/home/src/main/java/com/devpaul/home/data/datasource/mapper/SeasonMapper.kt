package com.devpaul.home.data.datasource.mapper

import com.devpaul.home.data.datasource.dto.response.SeasonDetailResponse
import com.devpaul.home.data.datasource.dto.response.SeasonResponse
import com.devpaul.home.data.datasource.dto.response.SeasonYearResponse
import com.devpaul.home.domain.entity.SeasonDetailEntity
import com.devpaul.home.domain.entity.SeasonEntity
import com.devpaul.home.domain.entity.SeasonYearEntity

fun SeasonResponse.toDomain(): SeasonEntity {
    return SeasonEntity(
        years = mapValues { (_, value) -> value.toDomain() }
    )
}

fun SeasonYearResponse.toDomain(): SeasonYearEntity {
    return SeasonYearEntity(
        autumn = autumn?.toDomain(),
        winter = winter?.toDomain(),
        spring = spring?.toDomain(),
        summer = summer?.toDomain(),
    )
}

fun SeasonDetailResponse.toDomain(): SeasonDetailEntity {
    return SeasonDetailEntity(
        startHour = startHour,
        startDay = startDay,
        startMonth = startMonth,
        startDate = startDate,
        startText = startText,
        endDate = endDate,
    )
}