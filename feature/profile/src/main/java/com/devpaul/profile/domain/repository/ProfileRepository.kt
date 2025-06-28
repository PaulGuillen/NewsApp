package com.devpaul.profile.domain.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.profile.domain.entity.ProfileEntity

interface ProfileRepository {
    suspend fun profileById(uid: String): DefaultOutput<ProfileEntity>
}