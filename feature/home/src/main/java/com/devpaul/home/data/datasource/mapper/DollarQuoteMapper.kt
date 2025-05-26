package com.devpaul.home.data.datasource.mapper

import com.devpaul.home.data.datasource.dto.response.DollarQuoteData
import com.devpaul.home.data.datasource.dto.response.DollarQuoteResponse
import com.devpaul.home.data.datasource.dto.response.PriceItem
import com.devpaul.home.domain.entity.DollarQuoteDataEntity
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.PriceItemEntity

fun DollarQuoteResponse.toDomain(): DollarQuoteEntity {
    return DollarQuoteEntity(
        status = status,
        message = message,
        data = data.toDomain()
    )
}

fun DollarQuoteData.toDomain(): DollarQuoteDataEntity {
    return DollarQuoteDataEntity(
        service = service,
        site = site,
        link = link,
        prices = prices?.map { it.toDomain() },
        note = note,
        usdToEuro = usdToEuro,
        date = date
    )
}

fun PriceItem.toDomain(): PriceItemEntity {
    return PriceItemEntity(
        buy = buy,
        sell = sell
    )
}