package com.devpaul.news.data.datasource.mapper

import com.devpaul.news.data.datasource.dto.res.RedditData
import com.devpaul.news.data.datasource.dto.res.RedditPost
import com.devpaul.news.data.datasource.dto.res.RedditResponse
import com.devpaul.news.domain.entity.RedditDataEntity
import com.devpaul.news.domain.entity.RedditEntity
import com.devpaul.news.domain.entity.RedditPostEntity

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

fun RedditPost.toDomain(): RedditPostEntity {
    return RedditPostEntity(
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
        media = media,
        mediaMetadata = mediaMetadata
    )
}