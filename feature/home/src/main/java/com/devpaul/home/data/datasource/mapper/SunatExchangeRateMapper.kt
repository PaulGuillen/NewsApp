package com.devpaul.home.data.datasource.mapper

import com.devpaul.home.data.datasource.dto.response.SunatExchangeRateResponse
import com.devpaul.home.domain.entity.SunatExchangeRateEntity

fun SunatExchangeRateResponse.toDomain(): SunatExchangeRateEntity {
    return SunatExchangeRateEntity(
        source = source,
        buy = buy,
        sell = sell,
        currency = currency,
        date = date,
    )
}