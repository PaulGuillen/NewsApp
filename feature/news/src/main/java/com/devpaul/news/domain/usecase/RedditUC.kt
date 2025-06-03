package com.devpaul.news.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.news.domain.entity.RedditEntity
import com.devpaul.news.domain.repository.NewsRepository
import org.koin.core.annotation.Factory

@Factory
class RedditUC(
    private val newsRepository: NewsRepository,
) : SimpleUC.ParamsAndResult<RedditUC.Params, DefaultOutput<RedditUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return newsRepository.redditService(
            country = params.country,
            page = params.page,
            perPage = params.perPage,
        )
            .transformHttpError {
                Failure.RedditError(it)
            }
            .transform {
                Success.RedditSuccess(it)
            }
    }

    data class Params(
        val country: String,
        val page: Int,
        val perPage: Int
    )

    sealed class Failure : Defaults.CustomError() {
        data class RedditError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class RedditSuccess(val reddit: RedditEntity) : Success()
    }
}