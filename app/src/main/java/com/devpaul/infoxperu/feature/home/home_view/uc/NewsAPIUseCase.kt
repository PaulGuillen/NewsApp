package com.devpaul.infoxperu.feature.home.home_view.uc

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.ArticleNewsResponse
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.feature.home.home_view.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsAPIUseCase @Inject constructor(
    private val repository: NewsRepository,
) {
    suspend operator fun invoke(initLetters: String): ResultState<NewsResponse> {
        return try {
            withContext(Dispatchers.IO) {
                val response = repository.newsAPI(initLetters)
                val sortedNewsItems = response.articles.sortedByDescending {
                    parseDate(it.publishDate)
                }
                val limitedNewsItems = limitNewsItems(sortedNewsItems, 20)
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
        return newsItems.take(limit)
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
