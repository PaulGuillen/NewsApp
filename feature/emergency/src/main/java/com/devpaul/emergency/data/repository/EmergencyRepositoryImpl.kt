package com.devpaul.emergency.data.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.emergency.data.datasource.remote.EmergencyServiceDS
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.entity.SectionEntity
import com.devpaul.emergency.domain.repository.EmergencyRepository
import org.koin.core.annotation.Factory

@Factory([EmergencyRepository::class])
class EmergencyRepositoryImpl(
    private val emergencyApi: EmergencyServiceDS,
) : EmergencyRepository {

    override suspend fun sectionService(): DefaultOutput<SectionEntity> {
        return emergencyApi.sectionService()
    }

    override suspend fun generalService(
        page: Int,
        perPage: Int,
    ): DefaultOutput<GeneralEntity> {
        return emergencyApi.generalService(page, perPage)
    }

    override suspend fun civilDefenseService(
        page: Int,
        perPage: Int,
    ): DefaultOutput<GeneralEntity> {
        return emergencyApi.civilDefenseService(page, perPage)
    }

    override suspend fun limaSecurityService(
        page: Int,
        perPage: Int,
    ): DefaultOutput<GeneralEntity> {
        return emergencyApi.limaSecurityService(page, perPage)
    }

    override suspend fun provincesSecurityService(
        page: Int,
        perPage: Int,
    ): DefaultOutput<GeneralEntity> {
        return emergencyApi.provincesSecurityService(page, perPage)
    }
}