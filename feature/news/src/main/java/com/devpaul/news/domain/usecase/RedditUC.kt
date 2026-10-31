package com.devpaul.news.domain.usecase

import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.error.ApiException
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.news.domain.entity.RedditEntity
import com.devpaul.news.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class RedditUC(
    private val newsRepository: NewsRepository,
) : suspend (RedditUC.Params) -> ResultState<RedditEntity> {

    override suspend fun invoke(params: Params): ResultState<RedditEntity> {
        return try {
            withContext(Dispatchers.IO) {
                val response = newsRepository.redditService(params.country)

                val sortedNewsItems =
                    response.data.children
                        .sortedByDescending { it.data.createdUtc }

                val modifiedResponse =
                    response.copy(
                        data = response.data.copy(
                            children = sortedNewsItems
                        )
                    )

                ResultState.Success(modifiedResponse)
            }
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (exception: Exception) {
            val message = when {
                exception is ApiException && exception.code == 403 ->
                    "Reddit no permite consultar las noticias en este momento. Prueba otra fuente."
                exception is ApiException && exception.code == 429 ->
                    "Reddit ha recibido demasiadas consultas. Intenta de nuevo más tarde."
                else -> "No se pudieron cargar las noticias de Reddit. Intenta de nuevo."
            }
            ResultState.Error(message)
        }
    }

    data class Params(
        val country: String,
    )

    sealed class Failure : Defaults.CustomError() {
        data class RedditError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class RedditSuccess(val reddit: RedditEntity) : Success()
    }
}
