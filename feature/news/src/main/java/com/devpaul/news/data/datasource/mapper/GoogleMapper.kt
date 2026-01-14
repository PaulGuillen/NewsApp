package com.devpaul.news.data.datasource.mapper

import com.devpaul.news.data.datasource.dto.res.*
import com.devpaul.news.domain.entity.*

fun GoogleNewsXML.toDomain(): GoogleEntity {
    return GoogleEntity(
        status = 200,
        message = "Google ",
        data = this.mapToNewsDataJSON()
    )
}

fun GoogleNewsXML.mapToNewsDataJSON(
    perPage: Int = 20,
    currentPage: Int = 1
): GoogleNewsJSON {

    val items = this.channel.items.map { mapToNewsItemJSON(it) }
    val totalItems = items.size
    val totalPages = if (perPage == 0) 1
    else kotlin.math.ceil(totalItems / perPage.toDouble()).toInt()

    return GoogleNewsJSON(
        title = channel.title,
        link = channel.link,
        language = channel.language,
        lastBuildDate = channel.lastBuildDate,
        description = channel.description,
        totalItems = totalItems,
        totalPages = totalPages,
        currentPage = currentPage,
        perPage = perPage,
        newsItems = items,
    )
}

private fun mapToNewsItemJSON(newsItem: NewsItem): NewsItemJSON {
    return NewsItemJSON(
        title = newsItem.title,
        link = newsItem.link,
        guid = newsItem.guid,
        pubDate = newsItem.pubDate,
        description = newsItem.description,
        source = mapToNewsSourceJSON(newsItem.source),
    )
}

private fun mapToNewsSourceJSON(newsSource: NewsSource?): NewsSourceJSON {
    return NewsSourceJSON(
        url = newsSource?.url ?: "",
        name = newsSource?.name ?: "",
    )
}