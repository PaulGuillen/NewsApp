package com.devpaul.profile.data.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.profile.data.datasource.dto.req.CommentRequest
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.data.datasource.remote.ProfileServiceDS
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.entity.CommentEntity
import com.devpaul.profile.domain.entity.ProfileEntity
import com.devpaul.profile.domain.entity.GenericEntity
import com.devpaul.profile.domain.entity.GetCommentEntity
import com.devpaul.profile.domain.repository.ProfileRepository
import org.koin.core.annotation.Factory

@Factory([ProfileRepository::class])
class ProfileRepositoryImpl(
    private val profileServiceDS: ProfileServiceDS,
) : ProfileRepository {

    override suspend fun profileById(uid: String): DefaultOutput<ProfileEntity> {
        return profileServiceDS.getProfileById(uid = uid)
    }

    override suspend fun updateUserData(
        uid: String,
        profileUser: UpdateRequest
    ): DefaultOutput<GenericEntity> {
        return profileServiceDS.getUpdateUserData(uid = uid, profileUser = profileUser)
    }

    override suspend fun createPost(postRequest: CommentRequest): DefaultOutput<CommentEntity> {
        return profileServiceDS.createComment(postRequest = postRequest)
    }

    override suspend fun incrementLike(
        type: String,
        commentId: String
    ): DefaultOutput<GenericEntity> {
        return profileServiceDS.incrementLike(type = type, commentId = commentId)
    }

    override suspend fun getPost(): DefaultOutput<PostEntity> {
        return profileServiceDS.getPost()
    }

    override suspend fun getComments(): DefaultOutput<GetCommentEntity> {
        return profileServiceDS.getComments()
    }
}