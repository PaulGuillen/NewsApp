package com.devpaul.home.data.datasource.mapper

import com.devpaul.core_data.model.GoogleNewsJSON
import com.devpaul.core_data.model.GoogleNewsXML
import com.devpaul.core_data.model.NewsItem
import com.devpaul.core_data.model.NewsItemJSON
import com.devpaul.core_data.model.NewsSource
import com.devpaul.core_data.model.NewsSourceJSON

class GoogleNewsMapper {

    fun mapToGoogleNewsJSON(googleNewsXML: GoogleNewsXML): GoogleNewsJSON {
        return GoogleNewsJSON(
            title = googleNewsXML.channel.title,
            link = googleNewsXML.channel.link,
            language = googleNewsXML.channel.language,
            lastBuildDate = googleNewsXML.channel.lastBuildDate,
            description = googleNewsXML.channel.description,
            newsItems = googleNewsXML.channel.items.map { mapToNewsItemJSON(it) }
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
            url = newsSource!!.url,
            name = newsSource.name,
        )
    }
}