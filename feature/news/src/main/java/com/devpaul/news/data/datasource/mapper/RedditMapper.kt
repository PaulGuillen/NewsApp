package com.devpaul.news.data.datasource.mapper

import com.devpaul.news.data.datasource.dto.res.ImageResolution
import com.devpaul.news.data.datasource.dto.res.MediaEmbed
import com.devpaul.news.data.datasource.dto.res.MediaMetaData
import com.devpaul.news.data.datasource.dto.res.RedditData
import com.devpaul.news.data.datasource.dto.res.RedditPost
import com.devpaul.news.data.datasource.dto.res.RedditResponse
import com.devpaul.news.domain.entity.ImageResolutionEntity
import com.devpaul.news.domain.entity.MediaEmbedEntity
import com.devpaul.news.domain.entity.MediaMetaDataEntity
import com.devpaul.news.domain.entity.RedditDataEntity
import com.devpaul.news.domain.entity.RedditEntity
import com.devpaul.news.domain.entity.RedditPostItemEntity

fun RedditResponse.toDomain(): RedditEntity {
    return RedditEntity(
        status = status,
        message = message,
        data = data.toDomain()
    )
}

fun RedditData.toDomain(): RedditDataEntity {
    return RedditDataEntity(
        items = items.map { it.toDomain() },
        totalItems = totalItems,
        totalPages = totalPages,
        currentPage = currentPage,
        perPage = perPage,
    )
}

fun RedditPost.toDomain(): RedditPostItemEntity {
    return RedditPostItemEntity(
        id = id,
        title = title,
        author = author,
        subreddit = subreddit,
        selfText = selfText,
        url = url,
        thumbnail = thumbnail,
        createdUtc = createdUtc,
        createdAt = createdAt,
        numComments = numComments,
        ups = ups,
        permalink = permalink,
        isVideo = isVideo,
        authorFullname = authorFullname,
        saved = saved,
        subredditNamePrefixed = subredditNamePrefixed,
        hidden = hidden,
        linkFlairCssClass = linkFlairCssClass,
        thumbnailHeight = thumbnailHeight,
        thumbnailWidth = thumbnailWidth,
        linkFlairTextColor = linkFlairTextColor,
        upvoteRatio = upvoteRatio,
        authorFlairBackgroundColor = authorFlairBackgroundColor,
        subredditType = subredditType,
        totalAwardsReceived = totalAwardsReceived,
        authorFlairTemplateId = authorFlairTemplateId,
        isOriginalContent = isOriginalContent,
        isSelf = isSelf,
        score = score,
        domain = domain,
        allowLiveComments = allowLiveComments,
        selftextHtml = selftextHtml,
        likes = likes,
        stickied = stickied,
        over18 = over18,
        spoiler = spoiler,
        locked = locked,
        authorFlairText = authorFlairText,
        linkFlairText = linkFlairText,
        linkFlairBackgroundColor = linkFlairBackgroundColor,
        linkFlairType = linkFlairType,
        authorFlairType = authorFlairType,
        quarantine = quarantine,
        distinguished = distinguished,
        numCrossposts = numCrossposts,
        isRedditMediaDomain = isRedditMediaDomain,
        media = media?.toDomain(),
        mediaMetadata = mediaMetadata?.toDomain()
    )
}

fun MediaEmbed?.toDomain(): MediaEmbedEntity? {
    return this?.let {
        MediaEmbedEntity(
            content = it.content,
            width = it.width,
            height = it.height
        )
    }
}

fun MediaMetaData?.toDomain(): MediaMetaDataEntity? {
    return this?.let {
        MediaMetaDataEntity(
            status = it.status,
            e = it.e,
            m = it.m,
            p = it.p?.map { res -> res.toDomain() },
            s = it.s?.toDomain(),
            id = it.id
        )
    }
}

fun ImageResolution?.toDomain(): ImageResolutionEntity {
    return ImageResolutionEntity(
        y = this?.y,
        x = this?.x,
        u = this?.u
    )
}