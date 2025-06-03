package com.devpaul.news.data.datasource.mapper

import com.devpaul.news.data.datasource.dto.res.*
import com.devpaul.news.domain.entity.*

fun GoogleResponse.toDomain(): GoogleEntity {
    return GoogleEntity(
        status = status,
        message = message,
        data = data.toDomain()
    )
}

fun GoogleData.toDomain(): GoogleDataEntity {
    return GoogleDataEntity(
        items = items.map { it.toDomain() },
        totalItems = totalItems,
        totalPages = totalPages,
        currentPage = currentPage,
        perPage = perPage
    )
}

fun GoogleNewsItem.toDomain(): GoogleNewsItemEntity {
    return GoogleNewsItemEntity(
        title = title,
        link = link,
        description = description,
        pubDate = pubDate,
        source = source.toDomain(),
        guid = guid.toDomain()
    )
}

fun Source.toDomain(): SourceEntity {
    return SourceEntity(
        url = url,
        name = name
    )
}

fun Guid.toDomain(): GuidEntity {
    return GuidEntity(
        value = value,
        metadata = metadata.toDomain()
    )
}

fun GuidMeta.toDomain(): GuidMetaEntity {
    return GuidMetaEntity(
        isPermLink = isPermLink
    )
}