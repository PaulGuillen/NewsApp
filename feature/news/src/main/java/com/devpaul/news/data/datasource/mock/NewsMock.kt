package com.devpaul.news.data.datasource.mock

import com.devpaul.news.domain.entity.Article
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.domain.entity.DeltaProjectDataEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.GoogleNewsJSON
import com.devpaul.news.domain.entity.ListingDataEntity
import com.devpaul.news.domain.entity.MediaEmbedEntity
import com.devpaul.news.domain.entity.NewsItemJSON
import com.devpaul.news.domain.entity.NewsSourceJSON
import com.devpaul.news.domain.entity.PostDataEntity
import com.devpaul.news.domain.entity.PostDataWrapperEntity
import com.devpaul.news.domain.entity.RedditEntity

data class NewsMock(
    val countryMock: CountryEntity = CountryEntity(
        status = 200,
        message = "Success",
        data = listOf(
            CountryItemEntity(
                id = "1",
                summary = "Mock summary for country news",
                title = "Mock Country News Title",
                category = "Mock Category",
                imageUrl = "https://via.placeholder.com/150",
                initLetters = "MCN"
            )
        )
    ),

    val googleMock: GoogleEntity = GoogleEntity(
        status = 200,
        message = "Success",
        data = GoogleNewsJSON(
            title = "Noticias de prueba",
            link = "https://example.com/noticias",
            language = "es",
            lastBuildDate = "2025-06-16 10:00",
            description = "Descripción simulada de noticias de prueba.",
            totalItems = 1,
            totalPages = 1,
            currentPage = 1,
            perPage = 10,
            newsItems = listOf(
                NewsItemJSON(
                    title = "Título de noticia simulada",
                    link = "https://example.com/noticia",
                    description = "Esta es una descripción simulada para una noticia de prueba.",
                    pubDate = "2025-06-16 10:00",
                    source = NewsSourceJSON(
                        url = "https://example.com",
                        name = "Fuente de Prueba"
                    ),
                    guid = "mock-guid-12345"
                )
            ),
        )
    ),

    val redditMock: RedditEntity = RedditEntity(
        kind = "t3",
        data = ListingDataEntity(
            after = "t3_mockafter",
            before = null,
            children = listOf(
                PostDataWrapperEntity(
                    kind = "t3",
                    data = PostDataEntity(
                        approvedAtUtc = null,
                        subreddit = "mocksubreddit",
                        selfText = "This is a mock self text for testing.",
                        authorFullname = "mock_author_123",
                        saved = false,
                        modReasonTitle = null,
                        gilded = 0,
                        clicked = false,
                        title = "Mock Reddit Post Title",
                        subredditNamePrefixed = "r/mocksubreddit",
                        hidden = false,
                        pwls = 6,
                        linkFlairCssClass = null,
                        downs = 0,
                        thumbnailHeight = 140,
                        topAwardedType = null,
                        hideScore = false,
                        name = "t3_mockname",
                        quarantine = false,
                        linkFlairTextColor = "dark",
                        upvoteRatio = 0.95,
                        authorFlairBackgroundColor = null,
                        subredditType = "public",
                        ups = 123,
                        totalAwardsReceived = 1,
                        thumbnailWidth = 140,
                        authorFlairTemplateId = null,
                        isOriginalContent = false,
                        userReports = emptyList(),
                        secureMedia = MediaEmbedEntity(
                            content = null,
                            width = null,
                            height = null,
                        ),
                        isRedditMediaDomain = false,
                    )
                )
            )
        )
    ),


    val deltaMock: DeltaProjectDataEntity = DeltaProjectDataEntity(
        articles = listOf(
            Article(
                url = "https://example.com/delta-article",
                urlMobile = "https://m.example.com/delta-article",
                title = "Título de artículo simulado de Delta Project",
                seenDate = "2025-06-16 10:00",
                socialImage = "https://via.placeholder.com/150",
                domain = "example.com",
                language = "es",
                sourceCountry = "PE"
            )
        ),
    )

)