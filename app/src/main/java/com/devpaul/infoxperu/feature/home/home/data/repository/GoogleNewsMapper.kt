package com.devpaul.infoxperu.feature.home.home.data.repository

import com.devpaul.infoxperu.domain.models.res.GoogleNewsJSON
import com.devpaul.infoxperu.domain.models.res.GoogleNewsXML
import com.devpaul.infoxperu.domain.models.res.NewsItem
import com.devpaul.infoxperu.domain.models.res.NewsItemJSON
import com.devpaul.infoxperu.domain.models.res.NewsSource
import com.devpaul.infoxperu.domain.models.res.NewsSourceJSON

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
            source = mapToNewsSourceJSON(newsItem.source)
        )
    }

    private fun mapToNewsSourceJSON(newsSource: NewsSource?): NewsSourceJSON {
        return NewsSourceJSON(
            url = newsSource!!.url,
            name = newsSource.name
        )
    }
}
