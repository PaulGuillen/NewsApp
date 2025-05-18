package com.devpaul.home.domain.usecase

import com.devpaul.core_data.model.Article
import com.devpaul.core_data.model.GDELProject
import com.devpaul.core_data.viewmodel.CoroutineDispatcherProvider
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.domain.repository.HomeRepository
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Factory
class GDELTUseCase(
    private val repository: HomeRepository,
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