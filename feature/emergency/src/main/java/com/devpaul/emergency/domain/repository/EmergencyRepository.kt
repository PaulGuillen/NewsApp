package com.devpaul.emergency.domain.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.entity.SectionEntity

interface EmergencyRepository {

    suspend fun sectionService(): DefaultOutput<SectionEntity>

    suspend fun generalService(
        page: Int,
        perPage: Int,
    ): DefaultOutput<GeneralEntity>

    suspend fun civilDefenseService(
        page: Int,
        perPage: Int,
    ): DefaultOutput<GeneralEntity>

    suspend fun limaSecurityService(
        page: Int,
        perPage: Int,
    ): DefaultOutput<GeneralEntity>

    suspend fun provincesSecurityService(
        page: Int,
        perPage: Int,
    ): DefaultOutput<GeneralEntity>
}