package com.devpaul.profile.domain.entity

data class PostEntity(
    val status: Int,
    val data: List<PostItemEntity>
)

data class PostItemEntity(
    val id: String? = null,
    val createdAt: CreatedAtEntity? = null,
    val likes: Int? = null,
    val postId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val toPublic: Boolean? = null,
    val image: String? = null,
)

data class CreatedAtEntity(
    val seconds: Long,
    val nanoseconds: Int
)