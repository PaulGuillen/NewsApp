package com.devpaul.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RedditEntity(
    val kind: String,
    val data: ListingDataEntity
) : Parcelable

@Parcelize
data class ListingDataEntity(
    val after: String? = "",
    val dist: Int? = 0,
    val modhash: String? = "",
    val geoFilter: String? = "",
    val children: List<PostDataWrapperEntity>,
    val before: String? = ""
) : Parcelable

@Parcelize
data class PostDataWrapperEntity(
    val kind: String? = "",
    val data: PostDataEntity = PostDataEntity()
) : Parcelable

@Parcelize
data class PostDataEntity(
    val approvedAtUtc: String? = "",
    val subreddit: String? = "",
    val selfText: String? = "",
    val authorFullname: String? = "",
    val saved: Boolean? = false,
    val modReasonTitle: String? = "",
    val gilded: Int? = 0,
    val clicked: Boolean? = false,
    val title: String? = "",
    val subredditNamePrefixed: String? = "",
    val hidden: Boolean? = false,
    val pwls: Int? = 0,
    val linkFlairCssClass: String? = "",
    val downs: Int? = 0,
    val thumbnailHeight: Int? = 0,
    val topAwardedType: String? = "",
    val hideScore: Boolean? = false,
    val name: String? = "",
    val quarantine: Boolean? = false,
    val linkFlairTextColor: String? = "",
    val upvoteRatio: Double? = 0.0,
    val authorFlairBackgroundColor: String? = "",
    val subredditType: String? = "",
    val ups: Int? = 0,
    val totalAwardsReceived: Int? = 0,
    val thumbnailWidth: Int? = 0,
    val authorFlairTemplateId: String? = "",
    val isOriginalContent: Boolean? = false,
    val userReports: List<String>? = listOf(),
    val secureMedia: MediaEmbedEntity? = null,
    val isRedditMediaDomain: Boolean? = false,
    val isMeta: Boolean? = false,
    val category: String? = "",
    val linkFlairText: String? = "",
    val canModPost: Boolean? = false,
    val score: Int? = 0,
    val approvedBy: String? = "",
    val thumbnail: String? = "",
    val edited: String? = "",
    val authorFlairCssClass: String? = "",
    val authorFlairRichtext: List<String>? = listOf(),
    val gildings: String? = "",
    val contentCategories: String? = "",
    val isSelf: Boolean? = false,
    val modNote: String? = "",
    val createdAtMillis: Long ? = 0L,
    val linkFlairType: String? = "",
    val wls: Int? = 0,
    val removedByCategory: String? = "",
    val bannedBy: String? = "",
    val authorFlairType: String? = "",
    val domain: String? = "",
    val allowLiveComments: Boolean? = false,
    val selftextHtml: String? = "",
    val likes: String? = "",
    val suggestedSort: String? = "",
    val bannedAtUtc: String? = "",
    val viewCount: Int? = 0,
    val archived: Boolean? = false,
    val noFollow: Boolean? = false,
    val isCrosspostable: Boolean? = false,
    val pinned: Boolean? = false,
    val over18: Boolean? = false,
    val mediaMetadata: MediaMetaDataEntity? = null,
    val allAwardings: List<String>? = listOf(),
    val awarders: List<String>? = listOf(),
    val mediaOnly: Boolean? = false,
    val canGild: Boolean? = false,
    val spoiler: Boolean? = false,
    val locked: Boolean? = false,
    val authorFlairText: String? = "",
    val treatmentTags: List<String>? = listOf(),
    val visited: Boolean? = false,
    val removedBy: String? = "",
    val numReports: Int? = 0,
    val distinguished: String? = "",
    val subredditId: String? = "",
    val authorIsBlocked: Boolean? = false,
    val modReasonBy: String? = "",
    val removalReason: String? = "",
    val linkFlairBackgroundColor: String? = "",
    val id: String? = "",
    val isRobotIndexable: Boolean? = false,
    val reportReasons: String? = "",
    val author: String = "",
    val discussionType: String? = "",
    val numComments: Int? = 0,
    val sendReplies: Boolean? = false,
    val whitelistStatus: String? = "",
    val contestMode: Boolean? = false,
    val modReports: List<String>? = null,
    val authorPatreonFlair: Boolean? = false,
    val authorFlairTextColor: String? = "",
    val permalink: String? = "",
    val parentWhitelistStatus: String? = "",
    val stickied: Boolean? = false,
    val url: String? = "",
    val subredditSubscribers: Int? = 0,
    val createdUtc: Double? = 0.0,
    val numCrossposts: Int? = 0,
    val media: MediaEmbedEntity? = null,
    val isVideo: Boolean? = false
) : Parcelable

@Parcelize
data class MediaMetaDataEntity(
    val status: String? = "",
    val e: String? = "",
    val m: String? = "",
    val p: List<ImageResolutionEntity>? = listOf(),
    val s: ImageResolutionEntity? = null,
    val id: String? = ""
) : Parcelable


@Parcelize
data class ImageResolutionEntity(
    val y: Int? = 0,
    val x: Int? = 0,
    val u: String? = ""
) : Parcelable


@Parcelize
data class MediaEmbedEntity(
    val content: String? = "",
    val width: Int? = 0,
    val height: Int? = 0
) : Parcelable