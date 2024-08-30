package com.devpaul.infoxperu.feature.home.home_view.uc

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Article
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.feature.home.home_view.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
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
                val sortedArticles = sortArticlesByDate(validArticles)

                if (sortedArticles.isNotEmpty()) {
                    val limitedNews = limitArticles(sortedArticles, 10)
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
        return GDELProject(articles.take(limit))
    }
}
