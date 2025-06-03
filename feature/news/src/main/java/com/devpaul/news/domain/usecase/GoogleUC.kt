package com.devpaul.news.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.repository.NewsRepository
import org.koin.core.annotation.Factory

@Factory
class GoogleUC(
    private val newsRepository: NewsRepository,
) : SimpleUC.ParamsAndResult<GoogleUC.Params, DefaultOutput<GoogleUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return newsRepository.googleService(
            q = params.q,
            hl = params.hl,
            page = params.page,
            perPage = params.perPage
        )
            .transformHttpError {
                Failure.GoogleError(it)
            }
            .transform {
                Success.GoogleSuccess(it)
            }
    }

    data class Params(
        val q: String,
        val hl: String,
        val page: Int,
        val perPage: Int
    )

    sealed class Failure : Defaults.CustomError() {
        data class GoogleError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class GoogleSuccess(val google: GoogleEntity) : Success()
    }
}