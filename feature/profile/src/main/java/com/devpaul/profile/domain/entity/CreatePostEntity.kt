package com.devpaul.profile.domain.entity

data class CreatePostEntity(
    val status: Int,
    val message: String,
    val commentId: String,
    val data: CommentDataEntity
)

data class CommentDataEntity(
    val commentId: String,
    val userId: String,
    val name: String,
    val lastname: String,
    val image: String? = null,
    val comment: String,
    val createdAt: CreatedAtEntity,
    val likes: Int
)