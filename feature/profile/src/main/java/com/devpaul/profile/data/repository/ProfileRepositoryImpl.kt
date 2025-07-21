package com.devpaul.profile.data.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.profile.data.datasource.dto.req.CreatePostRequest
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.data.datasource.remote.ProfileServiceDS
import com.devpaul.profile.domain.entity.AllPostEntity
import com.devpaul.profile.domain.entity.CreatePostEntity
import com.devpaul.profile.domain.entity.IncrementLikeEntity
import com.devpaul.profile.domain.entity.ProfileEntity
import com.devpaul.profile.domain.entity.UpdateEntity
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
    ): DefaultOutput<UpdateEntity> {
        return profileServiceDS.getUpdateUserData(uid = uid, profileUser = profileUser)
    }

    override suspend fun createPost(postRequest: CreatePostRequest): DefaultOutput<CreatePostEntity> {
        return profileServiceDS.createPost(postRequest = postRequest)
    }

    override suspend fun incrementLike(commentId: String): DefaultOutput<IncrementLikeEntity> {
        return profileServiceDS.incrementLike(commentId = commentId)
    }

    override suspend fun getAllPosts(): DefaultOutput<AllPostEntity> {
        return profileServiceDS.getAllPosts()
    }
}