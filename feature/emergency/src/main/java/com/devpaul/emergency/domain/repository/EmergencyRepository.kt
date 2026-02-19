package com.devpaul.emergency.domain.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.entity.PoliceEntity
import com.devpaul.emergency.domain.entity.SectionEntity

interface EmergencyRepository {

    suspend fun sectionService(): SectionEntity

    suspend fun generalService(): GeneralEntity

    suspend fun policeService(type: String): PoliceEntity

    suspend fun civilDefenseService(): GeneralEntity
}