package com.devpaul.home.domain.usecase

import com.devpaul.core_domain.entity.Output
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.repository.HomeRepository
import org.koin.core.annotation.Factory

@Factory
class SectionUC(
    private val homeRepository: HomeRepository,
) {
    suspend fun sectionService(): Output<SectionEntity, Throwable> {
        return try {
            val section = homeRepository.sectionService()
            Output.Success(section)
        } catch (ex: Exception) {
            Output.Failure(ex)
        }
    }
}