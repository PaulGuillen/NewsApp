package com.devpaul.infoxperu.feature.home.home.domain.uc

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.CoroutineDispatcherProvider
import com.devpaul.infoxperu.domain.models.res.ArticleNewsResponse
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.feature.home.home.data.repository.NewsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsAPIUseCase @Inject constructor(
    private val repository: NewsRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider,
) {
    suspend operator fun invoke(limit: Int, initLetters: String): ResultState<NewsResponse> {
        return try {
            withContext(dispatcherProvider.io) {
                val response = repository.newsAPI(initLetters)
                val sortedNewsItems = response.articles.sortedByDescending {
                    parseDate(it.publishDate)
                }
                val limitedNewsItems = limitNewsItems(sortedNewsItems, limit)
                val modifiedResponse = response.copy(articles = limitedNewsItems)
                ResultState.Success(modifiedResponse)
            }
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

    private fun limitNewsItems(
        newsItems: List<ArticleNewsResponse>,
        limit: Int
    ): List<ArticleNewsResponse> {
        return if (limit == 0) {
            newsItems
        } else {
            newsItems.take(limit)
        }
    }

    private fun parseDate(dateString: String): Long {
        return try {
            val formatter = java.time.format.DateTimeFormatter.ISO_INSTANT
            val instant = java.time.Instant.from(formatter.parse(dateString))
            instant.toEpochMilli()
        } catch (e: Exception) {
            0L
        }
    }
}
