package com.devpaul.home.domain.usecase

import com.devpaul.core_data.model.ArticleNewsResponse
import com.devpaul.core_data.model.NewsResponse
import com.devpaul.core_data.viewmodel.CoroutineDispatcherProvider
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.domain.repository.HomeRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class NewsAPIUseCase(
    private val repository: HomeRepository,
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