package com.devpaul.profile.domain.entity

data class GetCommentEntity(
    val status: Int,
    val comments: List<GetCommentDataEntity>
)

data class GetCommentDataEntity(
    val id: String = "",
    val commentId: String = "",
    val userId: String = "",
    val name: String = "",
    val lastname: String = "",
    val image: String? = null,
    val comment: String = "",
    val createdAt: CreatedAtEntity,
    val likes: Int = 0
)