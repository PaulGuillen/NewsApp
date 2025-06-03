package com.devpaul.news.data.datasource.mapper

import com.devpaul.news.data.datasource.dto.res.CountryItem
import com.devpaul.news.data.datasource.dto.res.CountryResponse
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.CountryItemEntity

fun CountryResponse.toDomain(): CountryEntity {
    return CountryEntity(
        status = status,
        message = message,
        data = data.map { it.toDomain() }
    )
}

fun CountryItem.toDomain(): CountryItemEntity {
    return CountryItemEntity(
        id = id,
        summary = summary,
        title = title,
        category = category,
        imageUrl = imageUrl,
        initLetters = initLetters
    )
}