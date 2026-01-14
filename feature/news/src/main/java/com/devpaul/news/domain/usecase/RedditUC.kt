package com.devpaul.news.domain.usecase

import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.news.domain.entity.PostDataWrapperEntity
import com.devpaul.news.domain.entity.RedditEntity
import com.devpaul.news.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class RedditUC(
    private val newsRepository: NewsRepository,
) : suspend (RedditUC.Params) -> ResultState<RedditEntity> {

    override suspend operator fun invoke(params: Params): ResultState<RedditEntity> {
        return try {
            withContext(Dispatchers.IO) {
                val response = newsRepository.redditService(params.country)
                val sortedNewsItems =
                    response.data.children.sortedByDescending { it.data.createdUtc }
                val limitedNewsItems = limitNewsItems(sortedNewsItems, params.limit)
                val modifiedResponse =
                    response.copy(data = response.data.copy(children = limitedNewsItems))
                ResultState.Success(modifiedResponse)
            }
        } catch (e: Exception) {
            ResultState.Error(e.toString())
        }
    }

    data class Params(
        val country: String,
        val limit: Int,
    )

    sealed class Failure : Defaults.CustomError() {
        data class RedditError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class RedditSuccess(val reddit: RedditEntity) : Success()
    }

    private fun limitNewsItems(
        newsItems: List<PostDataWrapperEntity>,
        limit: Int
    ): List<PostDataWrapperEntity> {
        return if (limit == 0) {
            newsItems
        } else {
            newsItems.take(limit)
        }
    }

}