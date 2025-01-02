package com.devpaul.infoxperu.feature.home.home.domain.uc

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.CoroutineDispatcherProvider
import com.devpaul.infoxperu.domain.models.res.Article
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.feature.home.home.data.repository.NewsRepository
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GDELTUseCase @Inject constructor(
    private val repository: NewsRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider,
) {

    suspend operator fun invoke(
        limit: Int,
        query: String,
        mode: String,
        format: String
    ): ResultState<GDELProject> {
        return try {
            withContext(dispatcherProvider.io) {
                val response = repository.deltaProject(query, mode, format)
                val validArticles = filterValidArticles(response.articles)
                val sortedArticles = sortArticlesByDate(validArticles)

                if (sortedArticles.isNotEmpty()) {
                    val limitedNews = limitArticles(sortedArticles, limit)
                    ResultState.Success(limitedNews)
                } else {
                    ResultState.Error(Exception("No se encontraron artículos válidos."))
                }
            }
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

    private fun filterValidArticles(articles: List<Article>): List<Article> {
        return articles.filter { article ->
            article.isValid()
        }
    }

    private fun Article.isValid(): Boolean {
        return title.isNotBlank() ||
                socialImage.isNotBlank() ||
                seenDate.isNotBlank() ||
                domain.isNotBlank()
    }

    private fun sortArticlesByDate(articles: List<Article>): List<Article> {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssX")
        return articles.sortedByDescending {
            ZonedDateTime.parse(it.seenDate, dateFormatter)
        }
    }

    private fun limitArticles(articles: List<Article>, limit: Int): GDELProject {
        return if (limit == 0) {
            GDELProject(articles)
        } else {
            GDELProject(articles.take(limit))
        }
    }
}
