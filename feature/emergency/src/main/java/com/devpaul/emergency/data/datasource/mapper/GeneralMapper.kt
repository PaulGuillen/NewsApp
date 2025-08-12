package com.devpaul.emergency.data.datasource.mapper

import com.devpaul.emergency.data.datasource.dto.res.GeneralResponse
import com.devpaul.emergency.data.datasource.dto.res.GeneralResponseItem
import com.devpaul.emergency.data.datasource.dto.res.PaginationResponse
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.entity.GeneralEntityItem
import com.devpaul.emergency.domain.entity.PaginationEntity

fun GeneralResponse.toDomain() : GeneralEntity {
    return GeneralEntity(
        status = status,
        data = data.map { it.toDomain() },
        pagination = pagination.toDomain()
    )
}

fun GeneralResponseItem.toDomain() : GeneralEntityItem {
    return GeneralEntityItem(
        key = key,
        value = value
    )
}

fun PaginationResponse.toDomain() : PaginationEntity {
    return PaginationEntity(
        total = total,
        perPage = perPage,
        currentPage = currentPage,
        totalPages = totalPages
    )
}