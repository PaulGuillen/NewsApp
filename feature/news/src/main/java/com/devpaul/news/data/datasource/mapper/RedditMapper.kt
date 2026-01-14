package com.devpaul.news.data.datasource.mapper

import com.devpaul.infoxperu.domain.models.res.ListingData
import com.devpaul.infoxperu.domain.models.res.PostData
import com.devpaul.infoxperu.domain.models.res.PostDataWrapper
import com.devpaul.infoxperu.domain.models.res.RedditResponse
import com.devpaul.news.domain.entity.ListingDataEntity
import com.devpaul.news.domain.entity.PostDataEntity
import com.devpaul.news.domain.entity.PostDataWrapperEntity
import com.devpaul.news.domain.entity.RedditEntity

fun RedditResponse.toDomain(): RedditEntity {
    return RedditEntity(
        kind = kind,
        data = data.toEntity()
    )
}

fun ListingData.toEntity(): ListingDataEntity {
    return ListingDataEntity(
        after = after,
        dist = dist,
        modhash = modhash,
        geoFilter = geoFilter,
        children = children
            .filter { it.data.isValid() }
            .map { it.toEntity() },
        before = before
    )
}

fun PostDataWrapper.toEntity(): PostDataWrapperEntity {
    return PostDataWrapperEntity(
        kind = kind ?: "",
        data = data.toEntity()
    )
}

fun PostData.toEntity(): PostDataEntity {
    return PostDataEntity(
        approvedAtUtc = approvedAtUtc,
        subreddit = subreddit,
        selfText = selfText,
        authorFullname = authorFullname,
        saved = saved ?: false,
        modReasonTitle = modReasonTitle,
        gilded = gilded ?: 0,
        clicked = clicked ?: false,
        id = id.orEmpty(),
        title = title.orEmpty(),
        author = author,
        score = score ?: 0,
        numComments = numComments ?: 0,
        thumbnail = thumbnail.takeIf { it?.startsWith("http") == true },
        url = url,
        createdUtc = createdUtc ?: 0.0,
        createdAtMillis = ((created ?: 0.0) * 1000).toLong(),
        isVideo = isVideo ?: false,
        over18 = over18 ?: false,
        permalink = buildRedditUrl(permalink)
    )
}

private fun PostData.isValid(): Boolean {
    return !title.isNullOrBlank() &&
            !permalink.isNullOrBlank()
}

private fun buildRedditUrl(permalink: String?): String {
    return if (permalink.isNullOrBlank()) {
        ""
    } else {
        "https://www.reddit.com$permalink"
    }
}