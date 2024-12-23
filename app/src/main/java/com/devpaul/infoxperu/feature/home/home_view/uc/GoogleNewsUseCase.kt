package com.devpaul.infoxperu.feature.home.home_view.uc

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.CoroutineDispatcherProvider
import com.devpaul.infoxperu.domain.models.res.GoogleNewsJSON
import com.devpaul.infoxperu.domain.models.res.NewsItemJSON
import com.devpaul.infoxperu.feature.home.home_view.repository.GoogleNewsMapper
import com.devpaul.infoxperu.feature.home.home_view.repository.NewsRepository
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class GoogleNewsUseCase @Inject constructor(
    private val repository: NewsRepository,
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
