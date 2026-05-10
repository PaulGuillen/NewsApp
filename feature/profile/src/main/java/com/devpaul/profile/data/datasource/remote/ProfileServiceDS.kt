package com.devpaul.profile.data.datasource.remote

import com.devpaul.profile.data.datasource.dto.req.CommentRequest
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.domain.entity.CommentEntity
import com.devpaul.profile.domain.entity.GenericEntity
import com.devpaul.profile.domain.entity.GetCommentEntity
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.entity.ProfileEntity
import org.koin.core.annotation.Factory

@Factory
class ProfileServiceDS(
    private val firebaseProfileDS: FirebaseProfileDS
) {
    suspend fun getProfileById(uid: String): ProfileEntity {
        return firebaseProfileDS.getProfileById(uid)
    }

    suspend fun getUpdateUserData(
        uid: String,
        profileUser: UpdateRequest,
    ): GenericEntity {
        return firebaseProfileDS.updateUserData(uid, profileUser)
    }

    suspend fun createComment(
        postRequest: CommentRequest,
    ): CommentEntity {
        return firebaseProfileDS.createComment(postRequest)
    }

    suspend fun incrementLike(
        type: String,
        commentId: String,
        userId: String,
        increment: Boolean,
    ): GenericEntity {
        return firebaseProfileDS.incrementLike(
            type = type,
            commentId = commentId,
            userId = userId,
            increment = increment
        )
    }

    suspend fun getPost(): PostEntity {
        return firebaseProfileDS.getPost()
    }

    suspend fun getComments(
        limit: Int,
        lastTimestamp: Long? = null
    ): GetCommentEntity {
        return firebaseProfileDS.getComments(
            limit = limit,
            lastTimestamp = lastTimestamp
        )
    }
}