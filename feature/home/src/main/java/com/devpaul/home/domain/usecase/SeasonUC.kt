package com.devpaul.home.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.home.domain.entity.SeasonEntity
import com.devpaul.home.domain.repository.HomeRepository
import org.koin.core.annotation.Factory

@Factory
class SeasonUC(
    private val homeRepository: HomeRepository,
) : SimpleUC.OnlyResult<DefaultOutput<SeasonUC.Success>> {

    override suspend fun invoke(): DefaultOutput<Success> {
        return homeRepository.seasonService()
            .transformHttpError {
                Failure.SeasonError(it)
            }
            .transform {
                Success.SeasonSuccess(it)
            }
    }

    sealed class Failure : Defaults.CustomError() {
        data class SeasonError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class SeasonSuccess(val season: SeasonEntity) : Success()
    }
}