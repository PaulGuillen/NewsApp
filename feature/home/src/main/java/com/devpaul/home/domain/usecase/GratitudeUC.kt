package com.devpaul.home.domain.usecase

import com.devpaul.core_domain.entity.Output
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.repository.HomeRepository
import org.koin.core.annotation.Factory

@Factory
class GratitudeUC(
    private val homeRepository: HomeRepository,
) {
    suspend fun gratitudeService(): Output<GratitudeEntity, Throwable> {
        return try {
            val gratitude = homeRepository.gratitudeService()
            Output.Success(gratitude)
        } catch (ex: Exception) {
            Output.Failure(ex)
        }
    }
}