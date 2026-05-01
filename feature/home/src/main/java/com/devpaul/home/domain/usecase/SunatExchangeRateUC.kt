package com.devpaul.home.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.home.domain.entity.SunatExchangeRateEntity
import com.devpaul.home.domain.repository.HomeRepository
import org.koin.core.annotation.Factory

@Factory
class SunatExchangeRateUC(
    private val homeRepository: HomeRepository,
) : SimpleUC.OnlyResult<DefaultOutput<SunatExchangeRateUC.Success>> {

    override suspend fun invoke(): DefaultOutput<Success> {
        return homeRepository.sunatExchangeRateService()
            .transformHttpError {
                Failure.SunatExchangeRateError(it)
            }
            .transform {
                Success.SunatExchangeRateSuccess(it)
            }
    }

    sealed class Failure : Defaults.CustomError() {
        data class SunatExchangeRateError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class SunatExchangeRateSuccess(val exchangeRate: SunatExchangeRateEntity) : Success()
    }
}