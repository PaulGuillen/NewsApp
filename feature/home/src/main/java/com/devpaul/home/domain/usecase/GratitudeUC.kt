package com.devpaul.home.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.repository.HomeRepository
import org.koin.core.annotation.Factory

@Factory
class GratitudeUC(
    private val homeRepository: HomeRepository,
) : SimpleUC.OnlyResult<DefaultOutput<GratitudeUC.Success>> {

    override suspend fun invoke(): DefaultOutput<Success> {
        return homeRepository.gratitudeService()
            .transformHttpError {
                Failure.GratitudeError(it)
            }
            .transform {
                Success.GratitudeSuccess(it)
            }
    }

    sealed class Failure : Defaults.CustomError() {
        data class GratitudeError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class GratitudeSuccess(val uit: GratitudeEntity) : Success()
    }
}