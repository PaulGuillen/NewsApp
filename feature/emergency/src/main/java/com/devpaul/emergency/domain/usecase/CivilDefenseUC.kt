package com.devpaul.emergency.domain.usecase

import com.devpaul.core_domain.entity.Output
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.repository.EmergencyRepository
import org.koin.core.annotation.Factory

@Factory
class CivilDefenseUC(
    private val emergencyRepository: EmergencyRepository
) {

    suspend fun civilDefenseService(): Output<GeneralEntity, Throwable> {
        return try {
            val generalEntity: GeneralEntity = emergencyRepository.civilDefenseService()
            Output.Success(data = generalEntity)
        } catch (ex: Exception) {
            Output.Failure(error = ex)
        }
    }
}