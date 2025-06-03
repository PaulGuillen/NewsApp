package com.devpaul.news.data.datasource.mapper

import com.devpaul.news.data.datasource.dto.res.GDELTResponse
import com.devpaul.news.data.datasource.dto.res.GDELTData
import com.devpaul.news.data.datasource.dto.res.GDELTItem
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.DeltaProjectDataEntity
import com.devpaul.news.domain.entity.DeltaProjectItemEntity

fun GDELTResponse.toDomain(): DeltaProjectEntity {
    return DeltaProjectEntity(
        status = status,
        message = message,
        data = data.toDomain()
    )
}

fun GDELTData.toDomain(): DeltaProjectDataEntity {
    return DeltaProjectDataEntity(
        items = items.map { it.toDomain() },
        totalItems = totalItems,
        totalPages = totalPages,
        currentPage = currentPage,
        perPage = perPage
    )
}

fun GDELTItem.toDomain(): DeltaProjectItemEntity {
    return DeltaProjectItemEntity(
        url = url,
        urlMobile = urlMobile,
        title = title,
        seenDate = seenDate,
        socialImage = socialImage,
        domain = domain,
        language = language,
        sourceCountry = sourceCountry
    )
}