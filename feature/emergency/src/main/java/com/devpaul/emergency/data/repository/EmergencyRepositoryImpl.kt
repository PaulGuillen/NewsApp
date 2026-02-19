package com.devpaul.emergency.data.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.emergency.data.datasource.remote.EmergencyServiceDS
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.entity.PoliceEntity
import com.devpaul.emergency.domain.entity.SectionEntity
import com.devpaul.emergency.domain.repository.EmergencyRepository
import org.koin.core.annotation.Factory

@Factory([EmergencyRepository::class])
class EmergencyRepositoryImpl(
    private val emergencyApi: EmergencyServiceDS,
) : EmergencyRepository {

    override suspend fun sectionService(): SectionEntity {
        return emergencyApi.sectionService()
    }

    override suspend fun generalService(): GeneralEntity {
        return emergencyApi.generalService()
    }

    override suspend fun policeService(type: String): PoliceEntity {
        return emergencyApi.policeService(type)
    }

    override suspend fun civilDefenseService(): GeneralEntity {
        return emergencyApi.civilDefenseService()
    }
}