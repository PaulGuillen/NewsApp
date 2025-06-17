package com.devpaul.news.data.datasource.mock

import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.domain.entity.DeltaProjectDataEntity
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.DeltaProjectItemEntity
import com.devpaul.news.domain.entity.GoogleDataEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.GoogleNewsItemEntity
import com.devpaul.news.domain.entity.GuidEntity
import com.devpaul.news.domain.entity.GuidMetaEntity
import com.devpaul.news.domain.entity.ImageResolutionEntity
import com.devpaul.news.domain.entity.MediaEmbedEntity
import com.devpaul.news.domain.entity.MediaMetaDataEntity
import com.devpaul.news.domain.entity.RedditDataEntity
import com.devpaul.news.domain.entity.RedditEntity
import com.devpaul.news.domain.entity.RedditPostItemEntity
import com.devpaul.news.domain.entity.SourceEntity

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
        data = GoogleDataEntity(
            items = listOf(
                GoogleNewsItemEntity(
                    title = "Título de noticia simulada",
                    link = "https://example.com/noticia",
                    description = "Esta es una descripción simulada para una noticia de prueba.",
                    pubDate = "2025-06-16 10:00",
                    source = SourceEntity(
                        url = "https://example.com",
                        name = "Fuente de Prueba"
                    ),
                    guid = GuidEntity(
                        value = "guid-123",
                        metadata = GuidMetaEntity(
                            isPermLink = "true"
                        )
                    )
                )
            ),
            totalItems = 1,
            totalPages = 1,
            currentPage = 1,
            perPage = 10
        )
    ),

    val redditMock: RedditEntity = RedditEntity(
        status = 200,
        message = "Success",
        data = RedditDataEntity(
            items = listOf(
                RedditPostItemEntity(
                    id = "abc123",
                    title = "Título de prueba en Reddit",
                    author = "usuario_mock",
                    subreddit = "Peru",
                    selfText = "Texto simulado de una publicación de Reddit.",
                    url = "https://reddit.com/r/Peru/comments/abc123",
                    thumbnail = "https://via.placeholder.com/150",
                    createdUtc = 1687000000,
                    createdAt = "2025-06-16 10:00",
                    numComments = 42,
                    ups = 120,
                    permalink = "/r/Peru/comments/abc123",
                    isVideo = false,
                    authorFullname = "t2_mockuser",
                    saved = false,
                    subredditNamePrefixed = "r/Peru",
                    hidden = false,
                    linkFlairCssClass = null,
                    thumbnailHeight = 150,
                    thumbnailWidth = 150,
                    linkFlairTextColor = "dark",
                    upvoteRatio = 0.95,
                    authorFlairBackgroundColor = "#ffffff",
                    subredditType = "public",
                    totalAwardsReceived = 0,
                    authorFlairTemplateId = null,
                    isOriginalContent = true,
                    isSelf = true,
                    score = 300,
                    domain = "self.Peru",
                    allowLiveComments = true,
                    selftextHtml = null,
                    likes = null,
                    stickied = false,
                    over18 = false,
                    spoiler = false,
                    locked = false,
                    authorFlairText = "MockFlair",
                    linkFlairText = "Anuncio",
                    linkFlairBackgroundColor = "#eeeeee",
                    linkFlairType = "text",
                    authorFlairType = "text",
                    quarantine = false,
                    distinguished = null,
                    numCrossposts = 1,
                    isRedditMediaDomain = false,
                    media = MediaEmbedEntity(
                        content = null,
                        width = null,
                        height = null
                    ),
                    mediaMetadata = MediaMetaDataEntity(
                        status = "valid",
                        e = "Image",
                        m = "image/jpg",
                        p = listOf(
                            ImageResolutionEntity(
                                y = 100,
                                x = 100,
                                u = "https://via.placeholder.com/100"
                            ),
                            ImageResolutionEntity(
                                y = 150,
                                x = 150,
                                u = "https://via.placeholder.com/150"
                            )
                        ),
                        s = ImageResolutionEntity(
                            y = 200,
                            x = 200,
                            u = "https://via.placeholder.com/200"
                        ),
                        id = "media_mock_id"
                    )
                )
            ),
            totalItems = 1,
            totalPages = 1,
            currentPage = 1,
            perPage = 10
        )
    ),

    val deltaMock: DeltaProjectEntity = DeltaProjectEntity(
        status = 200,
        message = "Success",
        data = DeltaProjectDataEntity(
            items = listOf(
                DeltaProjectItemEntity(
                    url = "https://example.com/noticia-principal",
                    urlMobile = "https://m.example.com/noticia-principal",
                    title = "Noticia simulada sobre política y sociedad",
                    seenDate = "2025-06-16 09:30",
                    socialImage = "https://via.placeholder.com/300",
                    domain = "example.com",
                    language = "es",
                    sourceCountry = "PE"
                )
            ),
            totalItems = 1,
            totalPages = 1,
            currentPage = 1,
            perPage = 10
        )
    )
)