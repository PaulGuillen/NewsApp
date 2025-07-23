package com.devpaul.profile.data.datasource.remote

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.core_domain.entity.transform
import com.devpaul.profile.data.datasource.dto.req.CommentRequest
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.data.datasource.mapper.toDomain
import com.devpaul.profile.domain.entity.CommentEntity
import com.devpaul.profile.domain.entity.GenericEntity
import com.devpaul.profile.domain.entity.GetCommentEntity
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.entity.ProfileEntity
import org.koin.core.annotation.Factory

@Factory
class ProfileServiceDS(
    private val profileServiceDS: ProfileService
) {
    suspend fun getProfileById(uid: String): DefaultOutput<ProfileEntity> {
        return safeApiCall {
            profileServiceDS.getProfileById(uid)
        }.transform { it.toDomain() }
    }

    suspend fun getUpdateUserData(
        uid: String,
        profileUser: UpdateRequest,
    ): DefaultOutput<GenericEntity> {
        return safeApiCall {
            profileServiceDS.updateUserData(uid, profileUser)
        }.transform { it.toDomain() }
    }

    suspend fun createComment(
        postRequest: CommentRequest,
    ): DefaultOutput<CommentEntity> {
        return safeApiCall {
            profileServiceDS.createComment(request = postRequest)
        }.transform { it.toDomain() }
    }

    suspend fun incrementLike(type: String, commentId: String): DefaultOutput<GenericEntity> {
        return safeApiCall {
            profileServiceDS.incrementLike(type = type, commentId = commentId)
        }.transform { it.toDomain() }
    }

    suspend fun getPost(): DefaultOutput<PostEntity> {
        return safeApiCall {
            profileServiceDS.getPost()
        }.transform { it.toDomain() }
    }

    suspend fun getComments(): DefaultOutput<GetCommentEntity> {
        return safeApiCall {
            profileServiceDS.getComments()
        }.transform { it.toDomain() }
    }
}