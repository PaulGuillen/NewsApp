package com.devpaul.news.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.repository.NewsRepository
import org.koin.core.annotation.Factory

@Factory
class DeltaProjectUC(
    private val newsRepository: NewsRepository,
) : SimpleUC.ParamsAndResult<DeltaProjectUC.Params, DefaultOutput<DeltaProjectUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return newsRepository.deltaProjectService(
            q = params.q,
            mode = params.mode,
            format = params.format,
            page = params.page,
            perPage = params.perPage,
        )
            .transformHttpError {
                Failure.DeltaProjectError(it)
            }
            .transform {
                Success.DeltaProjectSuccess(it)
            }
    }

    data class Params(
        val q: String,
        val mode: String,
        val format: String,
        val page: Int,
        val perPage: Int
    )

    sealed class Failure : Defaults.CustomError() {
        data class DeltaProjectError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class DeltaProjectSuccess(val deltaProject: DeltaProjectEntity) : Success()
    }
}