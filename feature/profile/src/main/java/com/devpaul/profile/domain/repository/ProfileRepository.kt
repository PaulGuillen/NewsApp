package com.devpaul.profile.domain.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.domain.entity.ProfileEntity
import com.devpaul.profile.domain.entity.UpdateEntity

interface ProfileRepository {
    suspend fun profileById(uid: String): DefaultOutput<ProfileEntity>
    suspend fun updateUserData(uid: String, profileUser: UpdateRequest): DefaultOutput<UpdateEntity>
}