package com.devpaul.emergency.domain.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.emergency.domain.entity.SectionEntity

interface EmergencyRepository {

    suspend fun sectionService(): DefaultOutput<SectionEntity>
}