package com.devpaul.profile.domain.entity

data class AllPostEntity(
    val status: Int,
    val posts: List<PostItemEntity>,
    val data: List<PostItemEntity>
)

data class PostItemEntity(
    val id: String? = null,
    val commentId: String? = null,
    val userId: String? = null,
    val name: String? = null,
    val lastname: String? = null,
    val image: String? = null,
    val comment: String? = null,
    val createdAt: CreatedAtEntity? = null,
    val likes: Int? = null,
    val postId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val toPublic: Boolean? = null
)

data class CreatedAtEntity(
    val seconds: Long,
    val nanoseconds: Int
)