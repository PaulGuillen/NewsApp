package com.devpaul.shared.mocks

import com.devpaul.core_data.model.Article
import com.devpaul.core_data.model.ArticleNewsResponse
import com.devpaul.core_data.model.Contact
import com.devpaul.core_data.model.Country
import com.devpaul.core_data.model.GDELProject
import com.devpaul.core_data.model.GoogleNewsJSON
import com.devpaul.core_data.model.ListingData
import com.devpaul.core_data.model.NewsItemJSON
import com.devpaul.core_data.model.NewsResponse
import com.devpaul.core_data.model.NewsSourceJSON
import com.devpaul.core_data.model.PostData
import com.devpaul.core_data.model.PostDataWrapper
import com.devpaul.core_data.model.RedditResponse
import com.devpaul.core_data.model.Service
import com.devpaul.core_data.model.SourceResponse

data class ContactMock(
    val contactListMock: List<Contact> = listOf(
        Contact(title = "Policía", type = "policia", imageUrl = ""),
        Contact(title = "Bomberos", type = "bombero", imageUrl = ""),
        Contact(title = "Serenazgo", type = "serenazgo", imageUrl = "")
    ),
    val singleContactMock: Contact = Contact(title = "Policía", type = "policia", imageUrl = "")
)

data class ServiceMock(
    val serviceListMock: List<Service> = listOf(
        Service(
            title = "Policia de Ancon",
            numberOne = "2872621",
            numberTwo = "2872620",
            description = "Descripcion de la policia de ancon...",
            imageOne = "",
            imageTwo = "",
            imageThree = "",
        ),
        Service(
            title = "Policía Ancon 2",
            numberOne = "2159595",
            numberTwo = "2926262",
            description = "Descripcion de la policia de ancon 2...",
            imageOne = "",
            imageTwo = "",
            imageThree = "",
        ),
    ),
    val singleServiceMock: Service = Service(
        title = "Policía Ancon",
        numberOne = "2159595",
        numberTwo = "2926262",
        description = "Descripcion de la policia de ancon 2...",
        imageOne = "",
        imageTwo = "",
        imageThree = "",
    ),
)

data class CountryMock(
    val countryListMock: List<Country> = listOf(
        Country(
            title = "Peru",
            category = "peru",
            summary = "Resumen",
            imageUrl = "https:www.reddit.com",
        ),
        Country(
            title = "Argentina",
            category = "argentina",
            summary = "Resumen",
            imageUrl = "https:www.apis.com",
        ),
        Country(
            title = "Ecuador",
            category = "ecuador",
            summary = "Resumen",
            imageUrl = "https:www.facebook.com",
        ),
    )
)

data class GoogleNewsMock(
    val googleNewsMock: GoogleNewsJSON =
        GoogleNewsJSON(
            title = "Peru",
            link = "https:www.test.com",
            language = "es",
            lastBuildDate = "2021-09-02",
            description = "Descripción Google",
            newsItems = listOf(
                NewsItemJSON(
                    title = "Primero",
                    link = "https:www.primero.com",
                    guid = "123",
                    pubDate = "2021-09-02",
                    description = "Descrip Primero",
                    source = NewsSourceJSON(
                        url = "https:www.primero.com",
                        name = "Info Perú"
                    )
                ),
                NewsItemJSON(
                    title = "Segundo",
                    link = "https:www.segundo.com",
                    guid = "123",
                    pubDate = "2021-10-05",
                    description = "Descripción Segundo",
                    source = NewsSourceJSON(
                        url = "https:www.segundo.com",
                        name = "Ifo Peru"
                    )
                ),
                NewsItemJSON(
                    title = "Tercero",
                    link = "https:www.google2.com",
                    guid = "123",
                    pubDate = "2021-09-01",
                    description = "Descripción",
                    source = NewsSourceJSON(
                        url = "https:www.google.com",
                        name = "Infobae Perú"
                    )
                )
            )
        )
)

data class GDELProjectMock(
    val deltaProjectMock: GDELProject = GDELProject(
        listOf(
            Article(
                url = "https:www.deperu.com/tv/wQfz0Keo-Os.venezuela-vs-canada-penales-resumen-y-goles-copa-america-2024-libero.UCk2OZrA0E6q6xp4bBKtf9KA.html",
                urlMobile = "",
                title = "Video : ? VENEZUELA VS CANADÁ PENALES , RESUMEN Y GOLES - COPA AMÉRICA 2024",
                seenDate = "20240707T020000Z",
                socialImage = "https:i.ytimg.com/vi/wQfz0Keo-Os/hqdefault.jpg",
                domain = "deperu.com",
                language = "Galician",
                sourceCountry = "United States",
            )
        )
    )
)

data class RedditMock(
    val redditMock: RedditResponse = RedditResponse(
        kind = "Listing",
        data = ListingData(
            after = "123",
            dist = 1,
            modhash = "123",
            geoFilter = "123",
            children = listOf(
                PostDataWrapper(
                    kind = "t3",
                    data = PostData(
                        subreddit = "peru",
                        title = "Peru",
                        selfText = "Peru",
                        authorFullname = "123",
                        saved = false,
                        gilded = 0,
                        clicked = false,
                        subredditNamePrefixed = "r/peru",
                        hidden = false,
                        pwls = 0,
                        linkFlairCssClass = "123",
                        downs = 0,
                        hideScore = false,
                        name = "123",
                        quarantine = false,
                        linkFlairTextColor = "123",
                        upvoteRatio = 0.0,
                        subredditType = "123",
                        ups = 0,
                        totalAwardsReceived = 0,
                        isOriginalContent = false,
                        userReports = emptyList(),
                        secureMedia = null,
                        isRedditMediaDomain = false,
                        isMeta = false,
                        category = "123",
                        linkFlairText = "123",
                        canModPost = false,
                        score = 0,
                        approvedBy = "123",
                    )
                )
            ),
            before = "123",
        )
    )

)

data class NewsMock(
    val newsMock: NewsResponse = NewsResponse(
        status = "ok",
        totalResults = 1,
        articles = listOf(
            ArticleNewsResponse(
                source = SourceResponse(
                    id = null,
                    name = "Infobae Perú"
                ),
                author = "Infobae Perú",
                title = "Peru",
                articleDescription = "Peru",
                url = "https:www.google.com",
                imageUrl = "https:www.google.com",
                publishDate = "2021-09-01",
                content = "Peru"
            )
        )
    )
)