package com.devpaul.home.domain.usecase

import com.devpaul.core_data.model.GoogleNewsJSON
import com.devpaul.core_data.model.NewsItemJSON
import com.devpaul.core_data.viewmodel.CoroutineDispatcherProvider
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.data.datasource.mapper.GoogleNewsMapper
import com.devpaul.home.domain.repository.HomeRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import java.text.SimpleDateFormat
import java.util.Locale

@Factory
class GoogleNewsUseCase(
    private val repository: HomeRepository,
    private val mapper: GoogleNewsMapper,
    private val dispatcherProvider: CoroutineDispatcherProvider,
) {
    suspend operator fun invoke(
        limit: Int,
        query: String,
        language: String
    ): ResultState<GoogleNewsJSON> {
        return try {
            withContext(dispatcherProvider.io) {
                val response = repository.googleNews(query, language)
                val mappedResponse = mapper.mapToGoogleNewsJSON(response)
                val sortedNewsItems = sortNewsItemsByDate(mappedResponse.newsItems)
                val limitedNewsItems = limitNewsItems(sortedNewsItems, limit)
                val limitedNews = mappedResponse.copy(newsItems = limitedNewsItems)
                ResultState.Success(limitedNews)
            }
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

    private fun sortNewsItemsByDate(newsItems: List<NewsItemJSON>): List<NewsItemJSON> {
        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        return newsItems.sortedByDescending {
            dateFormat.parse(it.pubDate.toString())
        }
    }

    private fun limitNewsItems(newsItems: List<NewsItemJSON>, limit: Int): List<NewsItemJSON> {
        return if (limit == 0) {
            newsItems
        } else {
            newsItems.take(limit)
        }
    }
}