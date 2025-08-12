package com.devpaul.emergency.data.datasource.remote

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.core_domain.entity.transform
import com.devpaul.emergency.data.datasource.mapper.toDomain
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.entity.SectionEntity
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

    suspend fun generalService(
        page: Int,
        perPage: Int,
    ): DefaultOutput<GeneralEntity> {
        return safeApiCall {
            emergencyService.general(page, perPage)
        }.transform { it.toDomain() }
    }

    suspend fun civilDefenseService(
        page: Int,
        perPage: Int,
    ): DefaultOutput<GeneralEntity> {
        return safeApiCall {
            emergencyService.civilDefense(page, perPage)
        }.transform { it.toDomain() }
    }
    suspend fun limaSecurityService(
        page: Int,
        perPage: Int,
    ): DefaultOutput<GeneralEntity> {
        return safeApiCall {
            emergencyService.limaSecurity(page, perPage)
        }.transform { it.toDomain() }
    }

    suspend fun provincesSecurityService(
        page: Int,
        perPage: Int,
    ): DefaultOutput<GeneralEntity> {
        return safeApiCall {
            emergencyService.provincesSecurity(page, perPage)
        }.transform { it.toDomain() }
    }
}