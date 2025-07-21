package com.devpaul.profile.data.datasource.remote

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.core_domain.entity.transform
import com.devpaul.profile.data.datasource.dto.req.CreatePostRequest
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.data.datasource.mapper.toDomain
import com.devpaul.profile.domain.entity.AllPostEntity
import com.devpaul.profile.domain.entity.CreatePostEntity
import com.devpaul.profile.domain.entity.IncrementLikeEntity
import com.devpaul.profile.domain.entity.ProfileEntity
import com.devpaul.profile.domain.entity.UpdateEntity
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
    ): DefaultOutput<UpdateEntity> {
        return safeApiCall {
            profileServiceDS.updateUserData(uid, profileUser)
        }.transform { it.toDomain() }
    }

    suspend fun createPost(
        postRequest: CreatePostRequest,
    ): DefaultOutput<CreatePostEntity> {
        return safeApiCall {
            profileServiceDS.createPost(request = postRequest)
        }.transform { it.toDomain() }
    }

    suspend fun incrementLike(commentId: String): DefaultOutput<IncrementLikeEntity> {
        return safeApiCall {
            profileServiceDS.incrementLike(commentId)
        }.transform { it.toDomain() }
    }

    suspend fun getAllPosts(): DefaultOutput<AllPostEntity> {
        return safeApiCall {
            profileServiceDS.getAllPosts()
        }.transform { it.toDomain() }
    }
}