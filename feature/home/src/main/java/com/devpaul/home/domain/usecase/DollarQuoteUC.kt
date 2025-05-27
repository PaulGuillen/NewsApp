package com.devpaul.home.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.repository.HomeRepository
import org.koin.core.annotation.Factory

@Factory
class DollarQuoteUC(
    private val homeRepository: HomeRepository,
) : SimpleUC.OnlyResult<DefaultOutput<DollarQuoteUC.Success>> {

    override suspend fun invoke(): DefaultOutput<Success> {
        return homeRepository.dollarQuoteService()
            .transformHttpError {
                Failure.DollarQuoteError(it)
            }
            .transform {
                Success.DollarQuoteSuccess(it)
            }
    }

    sealed class Failure : Defaults.CustomError() {
        data class DollarQuoteError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class DollarQuoteSuccess(val uit: DollarQuoteEntity) : Success()
    }
}