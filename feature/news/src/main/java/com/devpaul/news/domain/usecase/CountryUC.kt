package com.devpaul.news.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.repository.NewsRepository
import org.koin.core.annotation.Factory

@Factory
class CountryUC(
    private val newsRepository: NewsRepository,
) : SimpleUC.OnlyResult<DefaultOutput<CountryUC.Success>> {

    override suspend fun invoke(): DefaultOutput<Success> {
        return newsRepository.countryService()
            .transformHttpError {
                Failure.CountryError(it)
            }
            .transform {
                Success.CountrySuccess(it)
            }
    }

    sealed class Failure : Defaults.CustomError() {
        data class CountryError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class CountrySuccess(val country: CountryEntity) : Success()
    }
}