package com.devpaul.news.domain.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RedditEntity(
    val status: Int,
    val message: String,
    val data: RedditDataEntity
) : Parcelable

@Parcelize
data class RedditDataEntity(
    val items: List<RedditPostItemEntity>,
    val totalItems: Int,
    val totalPages: Int,
    val currentPage: Int,
    val perPage: Int
) : Parcelable

@Parcelize
data class RedditPostItemEntity(
    val id: String,
    val title: String,
    val author: String,
    val subreddit: String,
    val selfText: String,
    val url: String,
    val thumbnail: String,
    val createdUtc: Long,
    val createdAt: String,
    val numComments: Int,
    val ups: Int,
    val permalink: String,
    val isVideo: Boolean,
    val authorFullname: String,
    val saved: Boolean,
    val subredditNamePrefixed: String,
    val hidden: Boolean,
    val linkFlairCssClass: String?,
    val thumbnailHeight: Int?,
    val thumbnailWidth: Int?,
    val linkFlairTextColor: String?,
    val upvoteRatio: Double,
    val authorFlairBackgroundColor: String?,
    val subredditType: String,
    val totalAwardsReceived: Int,
    val authorFlairTemplateId: String?,
    val isOriginalContent: Boolean,
    val isSelf: Boolean,
    val score: Int,
    val domain: String,
    val allowLiveComments: Boolean,
    val selftextHtml: String?,
    val likes: Boolean?,
    val stickied: Boolean,
    val over18: Boolean,
    val spoiler: Boolean,
    val locked: Boolean,
    val authorFlairText: String?,
    val linkFlairText: String?,
    val linkFlairBackgroundColor: String?,
    val linkFlairType: String?,
    val authorFlairType: String?,
    val quarantine: Boolean,
    val distinguished: String?,
    val numCrossposts: Int,
    val isRedditMediaDomain: Boolean,
    val media: MediaEmbedEntity?,
    val mediaMetadata: MediaMetaDataEntity?
) : Parcelable

@Parcelize
data class MediaEmbedEntity(
    val content: String?,
    val width: Int?,
    val height: Int?
) : Parcelable

@Parcelize
data class MediaMetaDataEntity(
    val status: String?,
    val e: String?,
    val m: String? = "",
    val p: List<ImageResolutionEntity>?,
    val s: ImageResolutionEntity?,
    val id: String?,
) : Parcelable

@Parcelize
data class ImageResolutionEntity(
    val y: Int?,
    val x: Int?,
    val u: String?,
): Parcelable