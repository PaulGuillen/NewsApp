package com.devpaul.news.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class RedditResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: RedditData
)

data class RedditData(
    @SerializedName("items") val items: List<RedditPost>,
    @SerializedName("totalItems") val totalItems: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("currentPage") val currentPage: Int,
    @SerializedName("perPage") val perPage: Int
)

data class RedditPost(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("subreddit") val subreddit: String,
    @SerializedName("selfText") val selfText: String,
    @SerializedName("url") val url: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("createdUtc") val createdUtc: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("numComments") val numComments: Int,
    @SerializedName("ups") val ups: Int,
    @SerializedName("permalink") val permalink: String,
    @SerializedName("isVideo") val isVideo: Boolean,
    @SerializedName("authorFullname") val authorFullname: String,
    @SerializedName("saved") val saved: Boolean,
    @SerializedName("subredditNamePrefixed") val subredditNamePrefixed: String,
    @SerializedName("hidden") val hidden: Boolean,
    @SerializedName("linkFlairCssClass") val linkFlairCssClass: String?,
    @SerializedName("thumbnailHeight") val thumbnailHeight: Int?,
    @SerializedName("thumbnailWidth") val thumbnailWidth: Int?,
    @SerializedName("linkFlairTextColor") val linkFlairTextColor: String?,
    @SerializedName("upvoteRatio") val upvoteRatio: Double,
    @SerializedName("authorFlairBackgroundColor") val authorFlairBackgroundColor: String?,
    @SerializedName("subredditType") val subredditType: String,
    @SerializedName("totalAwardsReceived") val totalAwardsReceived: Int,
    @SerializedName("authorFlairTemplateId") val authorFlairTemplateId: String?,
    @SerializedName("isOriginalContent") val isOriginalContent: Boolean,
    @SerializedName("isSelf") val isSelf: Boolean,
    @SerializedName("score") val score: Int,
    @SerializedName("domain") val domain: String,
    @SerializedName("allowLiveComments") val allowLiveComments: Boolean,
    @SerializedName("selftextHtml") val selftextHtml: String?,
    @SerializedName("likes") val likes: Boolean?,
    @SerializedName("stickied") val stickied: Boolean,
    @SerializedName("over18") val over18: Boolean,
    @SerializedName("spoiler") val spoiler: Boolean,
    @SerializedName("locked") val locked: Boolean,
    @SerializedName("authorFlairText") val authorFlairText: String?,
    @SerializedName("linkFlairText") val linkFlairText: String?,
    @SerializedName("linkFlairBackgroundColor") val linkFlairBackgroundColor: String?,
    @SerializedName("linkFlairType") val linkFlairType: String?,
    @SerializedName("authorFlairType") val authorFlairType: String?,
    @SerializedName("quarantine") val quarantine: Boolean,
    @SerializedName("distinguished") val distinguished: String?,
    @SerializedName("numCrossposts") val numCrossposts: Int,
    @SerializedName("isRedditMediaDomain") val isRedditMediaDomain: Boolean,
    @SerializedName("media") val media: String?,
    @SerializedName("mediaMetadata") val mediaMetadata: String?
)