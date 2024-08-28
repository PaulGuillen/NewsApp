package com.devpaul.infoxperu.domain.models.res

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "rss", strict = false)
data class GoogleNewsXML @JvmOverloads constructor(
    @field:Element(name = "channel")
    var channel: Channel = Channel()
)

@Root(name = "channel", strict = false)
data class Channel @JvmOverloads constructor(
    @field:Element(name = "generator", required = false)
    var generator: String? = null,
    @field:Element(name = "title")
    var title: String = "",
    @field:Element(name = "link")
    var link: String = "",
    @field:Element(name = "language")
    var language: String = "",
    @field:Element(name = "webMaster", required = false)
    var webMaster: String? = null,
    @field:Element(name = "copyright", required = false)
    var copyright: String? = null,
    @field:Element(name = "lastBuildDate", required = false)
    var lastBuildDate: String? = null,
    @field:Element(name = "description", required = false)
    var description: String? = null,
    @field:ElementList(name = "item", inline = true)
    var items: MutableList<NewsItem> = mutableListOf()
)

@Root(name = "item", strict = false)
data class NewsItem @JvmOverloads constructor(
    @field:Element(name = "title")
    var title: String = "",
    @field:Element(name = "link")
    var link: String = "",
    @field:Element(name = "guid", required = false)
    var guid: String? = null,
    @field:Element(name = "pubDate", required = false)
    var pubDate: String? = null,
    @field:Element(name = "description", required = false)
    var description: String? = null,
    @field:Element(name = "source", required = false)
    var source: NewsSource? = null
)

@Root(name = "source", strict = false)
data class NewsSource @JvmOverloads constructor(
    @field:Attribute(name = "url")
    var url: String = "",
    @field:Text
    var name: String = ""
)
