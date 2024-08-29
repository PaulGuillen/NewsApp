package com.devpaul.infoxperu.feature.home.home_view.uc

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Article
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.feature.home.home_view.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GDELTUseCase @Inject constructor(
    private val repository: NewsRepository,
) {
    suspend operator fun invoke(
        query: String,
        mode: String,
        format: String
    ): ResultState<GDELProject> {
        return try {
            withContext(Dispatchers.IO) {
                val response = repository.deltaProject(query, mode, format)
                val validArticles = filterValidArticles(response.articles)

                if (validArticles.isNotEmpty()) {
                    val limitedNews = response.copy(articles = validArticles)
                    ResultState.Success(limitedNews)
                } else {
                    ResultState.Error(Exception("No valid news items found."))
                }
            }
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

    private fun filterValidArticles(articles: List<Article>): List<Article> {
        return articles.filter { it.title.isNotBlank() || it.socialImage.isNotBlank() || it.seenDate.isNotBlank() || it.domain.isNotBlank() }
            .take(10)
    }
}
