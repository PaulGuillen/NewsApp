package com.devpaul.home.domain.usecase

import com.devpaul.core_data.model.PostDataWrapper
import com.devpaul.core_data.model.RedditResponse
import com.devpaul.core_data.viewmodel.CoroutineDispatcherProvider
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.domain.repository.HomeRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class RedditUseCase(
    private val repository: HomeRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider,
) {
    suspend operator fun invoke(limit: Int, country: String): ResultState<RedditResponse> {
        return try {
            withContext(dispatcherProvider.io) {
                val response = repository.redditNews(country)
                val sortedNewsItems =
                    response.data.children.sortedByDescending { it.data.createdUtc }
                val limitedNewsItems = limitNewsItems(sortedNewsItems, limit)
                val modifiedResponse =
                    response.copy(data = response.data.copy(children = limitedNewsItems))
                ResultState.Success(modifiedResponse)
            }
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

    private fun limitNewsItems(
        newsItems: List<PostDataWrapper>,
        limit: Int
    ): List<PostDataWrapper> {
        return if (limit == 0) {
            newsItems
        } else {
            newsItems.take(limit)
        }
    }
}