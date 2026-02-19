package com.devpaul.emergency.domain.usecase

import com.devpaul.core_domain.entity.Output
import com.devpaul.emergency.domain.entity.SectionEntity
import com.devpaul.emergency.domain.repository.EmergencyRepository
import org.koin.core.annotation.Factory

@Factory
class SectionUC(
    private val emergencyRepository: EmergencyRepository,
) {

    suspend fun sectionService(): Output<SectionEntity, Throwable> {
        return try {
            val sectionEntity: SectionEntity = emergencyRepository.sectionService()
            Output.Success(data = sectionEntity)
        } catch (ex: Exception) {
            Output.Failure(error = ex)
        }
    }
}