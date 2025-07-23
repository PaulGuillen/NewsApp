package com.devpaul.profile.domain.entity

import com.devpaul.profile.data.datasource.dto.res.CreatedAtResponse

data class GetCommentEntity(
    val status: Int,
    val comments: GetCommentDataEntity
)

data class GetCommentDataEntity(
    val id: String,
    val commentId: String,
    val userId: String,
    val name: String,
    val lastname: String,
    val image: String? = null,
    val comment: String,
    val createdAt: CreatedAtEntity,
    val likes: Int
)