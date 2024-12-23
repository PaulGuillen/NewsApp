package com.devpaul.infoxperu.feature.home.home_view.uc

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.CoroutineDispatcherProvider
import com.devpaul.infoxperu.domain.models.res.PostDataWrapper
import com.devpaul.infoxperu.domain.models.res.RedditResponse
import com.devpaul.infoxperu.feature.home.home_view.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RedditUseCase @Inject constructor(
    private val repository: NewsRepository,
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