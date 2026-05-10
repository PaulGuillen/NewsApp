package com.devpaul.profile.domain.repository

import com.devpaul.profile.data.datasource.dto.req.CommentRequest
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.domain.entity.CommentEntity
import com.devpaul.profile.domain.entity.GenericEntity
import com.devpaul.profile.domain.entity.GetCommentEntity
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.entity.ProfileEntity

interface ProfileRepository {
    suspend fun profileById(uid: String): ProfileEntity
    suspend fun updateUserData(
        uid: String,
        profileUser: UpdateRequest
    ): GenericEntity

    suspend fun createComment(postRequest: CommentRequest): CommentEntity
    suspend fun incrementLike(
        type: String,
        commentId: String,
        userId: String,
        increment: Boolean,
    ): GenericEntity

    suspend fun getPost(): PostEntity
    suspend fun getComments(
        limit: Int,
        lastTimestamp: Long? = null
    ): GetCommentEntity
}