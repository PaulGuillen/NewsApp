package com.devpaul.news.data.datasource.mapper

import com.devpaul.news.data.datasource.dto.res.ArticleResponse
import com.devpaul.news.data.datasource.dto.res.GDELTResponse
import com.devpaul.news.domain.entity.Article
import com.devpaul.news.domain.entity.DeltaProjectDataEntity

fun GDELTResponse.toDomain(): DeltaProjectDataEntity {
    return DeltaProjectDataEntity(
        articles = articleResponses.map { it.toDomain() }
    )
}

fun ArticleResponse.toDomain(): Article {
    return Article(
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