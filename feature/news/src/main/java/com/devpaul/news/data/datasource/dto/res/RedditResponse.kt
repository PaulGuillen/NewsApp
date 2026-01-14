package com.devpaul.infoxperu.domain.models.res

import com.google.gson.annotations.SerializedName

data class RedditResponse(
    @SerializedName("kind") val kind: String,
    @SerializedName("data") val data: ListingData
)

data class ListingData(
    @SerializedName("after") val after: String? = "",
    @SerializedName("dist") val dist: Int? = 0,
    @SerializedName("modhash") val modhash: String? = "",
    @SerializedName("geo_filter") val geoFilter: String? = "",
    @SerializedName("children") val children: List<PostDataWrapper>,
    @SerializedName("before") val before: String? = ""
)

data class PostDataWrapper(
    @SerializedName("kind") val kind: String? = "",
    @SerializedName("data") val data: PostData = PostData()
)

data class PostData(
    @SerializedName("approved_at_utc") val approvedAtUtc: String? = "",
    @SerializedName("subreddit") val subreddit: String? = "",
    @SerializedName("selftext") val selfText: String? = "",
    @SerializedName("author_fullname") val authorFullname: String? = "",
    @SerializedName("saved") val saved: Boolean? = false,
    @SerializedName("mod_reason_title") val modReasonTitle: String? = "",
    @SerializedName("gilded") val gilded: Int? = 0,
    @SerializedName("clicked") val clicked: Boolean? = false,
    @SerializedName("title") val title: String? = "",
    @SerializedName("subreddit_name_prefixed") val subredditNamePrefixed: String? = "",
    @SerializedName("hidden") val hidden: Boolean? = false,
    @SerializedName("pwls") val pwls: Int? = 0,
    @SerializedName("link_flair_css_class") val linkFlairCssClass: String? = "",
    @SerializedName("downs") val downs: Int? = 0,
    @SerializedName("thumbnail_height") val thumbnailHeight: Int? = 0,
    @SerializedName("top_awarded_type") val topAwardedType: String? = "",
    @SerializedName("hide_score") val hideScore: Boolean? = false,
    @SerializedName("name") val name: String? = "",
    @SerializedName("quarantine") val quarantine: Boolean? = false,
    @SerializedName("link_flair_text_color") val linkFlairTextColor: String? = "",
    @SerializedName("upvote_ratio") val upvoteRatio: Double? = 0.0,
    @SerializedName("author_flair_background_color") val authorFlairBackgroundColor: String? = "",
    @SerializedName("subreddit_type") val subredditType: String? = "",
    @SerializedName("ups") val ups: Int? = 0,
    @SerializedName("total_awards_received") val totalAwardsReceived: Int? = 0,
    @SerializedName("thumbnail_width") val thumbnailWidth: Int? = 0,
    @SerializedName("author_flair_template_id") val authorFlairTemplateId: String? = "",
    @SerializedName("is_original_content") val isOriginalContent: Boolean? = false,
    @SerializedName("user_reports") val userReports: List<Any>? = listOf(),
    @SerializedName("secure_media") val secureMedia: MediaEmbed? = null,
    @SerializedName("is_reddit_media_domain") val isRedditMediaDomain: Boolean? = false,
    @SerializedName("is_meta") val isMeta: Boolean? = false,
    @SerializedName("category") val category: String? = "",
    @SerializedName("link_flair_text") val linkFlairText: String? = "",
    @SerializedName("can_mod_post") val canModPost: Boolean? = false,
    @SerializedName("score") val score: Int? = 0,
    @SerializedName("approved_by") val approvedBy: String? = "",
    @SerializedName("thumbnail") val thumbnail: String? = "",
    @SerializedName("edited") val edited: Any? = "",
    @SerializedName("author_flair_css_class") val authorFlairCssClass: String? = "",
    @SerializedName("author_flair_richtext") val authorFlairRichtext: List<Any>? = listOf(),
    @SerializedName("gildings") val gildings: Any? = "",
    @SerializedName("content_categories") val contentCategories: Any? = "",
    @SerializedName("is_self") val isSelf: Boolean? = false,
    @SerializedName("mod_note") val modNote: String? = "",
    @SerializedName("created") val created: Double? = 0.0,
    @SerializedName("link_flair_type") val linkFlairType: String? = "",
    @SerializedName("wls") val wls: Int? = 0,
    @SerializedName("removed_by_category") val removedByCategory: String? = "",
    @SerializedName("banned_by") val bannedBy: String? = "",
    @SerializedName("author_flair_type") val authorFlairType: String? = "",
    @SerializedName("domain") val domain: String? = "",
    @SerializedName("allow_live_comments") val allowLiveComments: Boolean? = false,
    @SerializedName("selftext_html") val selftextHtml: String? = "",
    @SerializedName("likes") val likes: String? = "",
    @SerializedName("suggested_sort") val suggestedSort: String? = "",
    @SerializedName("banned_at_utc") val bannedAtUtc: String? = "",
    @SerializedName("view_count") val viewCount: Int? = 0,
    @SerializedName("archived") val archived: Boolean? = false,
    @SerializedName("no_follow") val noFollow: Boolean? = false,
    @SerializedName("is_crosspostable") val isCrosspostable: Boolean? = false,
    @SerializedName("pinned") val pinned: Boolean? = false,
    @SerializedName("over_18") val over18: Boolean? = false,
    @SerializedName("media_metadata") val mediaMetadata: MediaMetaData? = null,
    @SerializedName("all_awardings") val allAwardings: List<Any>? = listOf(),
    @SerializedName("awarders") val awarders: List<String>? = listOf(),
    @SerializedName("media_only") val mediaOnly: Boolean? = false,
    @SerializedName("can_gild") val canGild: Boolean? = false,
    @SerializedName("spoiler") val spoiler: Boolean? = false,
    @SerializedName("locked") val locked: Boolean? = false,
    @SerializedName("author_flair_text") val authorFlairText: String? = "",
    @SerializedName("treatment_tags") val treatmentTags: List<Any>? = listOf(),
    @SerializedName("visited") val visited: Boolean? = false,
    @SerializedName("removed_by") val removedBy: String? = "",
    @SerializedName("num_reports") val numReports: Int? = 0,
    @SerializedName("distinguished") val distinguished: String? = "",
    @SerializedName("subreddit_id") val subredditId: String? = "",
    @SerializedName("author_is_blocked") val authorIsBlocked: Boolean? = false,
    @SerializedName("mod_reason_by") val modReasonBy: String? = "",
    @SerializedName("removal_reason") val removalReason: String? = "",
    @SerializedName("link_flair_background_color") val linkFlairBackgroundColor: String? = "",
    @SerializedName("id") val id: String? = "",
    @SerializedName("is_robot_indexable") val isRobotIndexable: Boolean? = false,
    @SerializedName("report_reasons") val reportReasons: Any? = "",
    @SerializedName("author") val author: String = "",
    @SerializedName("discussion_type") val discussionType: Any? = "",
    @SerializedName("num_comments") val numComments: Int? = 0,
    @SerializedName("send_replies") val sendReplies: Boolean? = false,
    @SerializedName("whitelist_status") val whitelistStatus: String? = "",
    @SerializedName("contest_mode") val contestMode: Boolean? = false,
    @SerializedName("mod_reports") val modReports: List<Any>? = null,
    @SerializedName("author_patreon_flair") val authorPatreonFlair: Boolean? = false,
    @SerializedName("author_flair_text_color") val authorFlairTextColor: String? = "",
    @SerializedName("permalink") val permalink: String? = "",
    @SerializedName("parent_whitelist_status") val parentWhitelistStatus: String? = "",
    @SerializedName("stickied") val stickied: Boolean? = false,
    @SerializedName("url") val url: String? = "",
    @SerializedName("subreddit_subscribers") val subredditSubscribers: Int? = 0,
    @SerializedName("created_utc") val createdUtc: Double? = 0.0,
    @SerializedName("num_crossposts") val numCrossposts: Int? = 0,
    @SerializedName("media") val media: MediaEmbed? = null,
    @SerializedName("is_video") val isVideo: Boolean? = false
)

data class MediaMetaData(
    @SerializedName("status") val status: String? = "",
    @SerializedName("e") val e: String? = "",
    @SerializedName("m") val m: String? = "",
    @SerializedName("p") val p: List<ImageResolution>? = listOf(),
    @SerializedName("s") val s: ImageResolution? = null,
    @SerializedName("id") val id: String? = ""
)

data class ImageResolution(
    @SerializedName("y") val y: Int? = 0,
    @SerializedName("x") val x: Int? = 0,
    @SerializedName("u") val u: String? = ""
)

data class MediaEmbed(
    @SerializedName("content") val content: String? = "",
    @SerializedName("width") val width: Int? = 0,
    @SerializedName("height") val height: Int? = 0
)
