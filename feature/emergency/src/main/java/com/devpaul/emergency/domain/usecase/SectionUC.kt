package com.devpaul.emergency.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.emergency.domain.entity.SectionEntity
import com.devpaul.emergency.domain.repository.EmergencyRepository
import org.koin.core.annotation.Factory

@Factory
class SectionUC(
    private val emergencyRepository: EmergencyRepository,
) : SimpleUC.OnlyResult<DefaultOutput<SectionUC.Success>> {

    override suspend fun invoke(): DefaultOutput<Success> {
        return emergencyRepository.sectionService()
            .transformHttpError {
                Failure.SectionError(it)
            }
            .transform {
                Success.SectionSuccess(it)
            }
    }

    sealed class Failure : Defaults.CustomError() {
        data class SectionError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class SectionSuccess(val section: SectionEntity) : Success()
    }
}