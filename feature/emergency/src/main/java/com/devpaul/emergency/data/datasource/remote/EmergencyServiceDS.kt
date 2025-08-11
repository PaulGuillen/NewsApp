package com.devpaul.emergency.data.datasource.remote

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.core_domain.entity.transform
import com.devpaul.emergency.domain.entity.SectionEntity
import com.devpaul.emergency.data.datasource.mapper.toDomain
import org.koin.core.annotation.Factory

@Factory
class EmergencyServiceDS(
    private val emergencyService: EmergencyService,
) {

    suspend fun sectionService(): DefaultOutput<SectionEntity> {
        return safeApiCall {
            emergencyService.section()
        }.transform { it.toDomain() }
    }
}