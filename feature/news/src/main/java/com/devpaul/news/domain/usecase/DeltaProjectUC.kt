package com.devpaul.news.domain.usecase

import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.news.domain.entity.Article
import com.devpaul.news.domain.entity.DeltaProjectDataEntity
import com.devpaul.news.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Factory
class DeltaProjectUC(
    private val newsRepository: NewsRepository,
) : suspend (DeltaProjectUC.Params) -> ResultState<DeltaProjectDataEntity> {

    override suspend fun invoke(params: Params): ResultState<DeltaProjectDataEntity> {
        return try {
            withContext(Dispatchers.IO) {
                val response = newsRepository.deltaProjectService(
                    q = params.q,
                    mode = params.mode,
                    format = params.format,
                )
                val validArticles = filterValidArticles(response.articles)
                val sortedArticles = sortArticlesByDate(validArticles)

                if (sortedArticles.isNotEmpty()) {
                    val limitedNews = limitArticles(sortedArticles, 10)
                    ResultState.Success(limitedNews)
                } else {
                    ResultState.Error("No se encontraron artículos válidos.")
                }
            }
        } catch (e: Exception) {
            ResultState.Error(e.toString())
        }
    }

    data class Params(
        val q: String,
        val mode: String,
        val format: String,
    )

    sealed class Failure : Defaults.CustomError() {
        data class DeltaProjectError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class DeltaProjectSuccess(val deltaProject: DeltaProjectDataEntity) : Success()
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

    private fun limitArticles(articles: List<Article>, limit: Int): DeltaProjectDataEntity {
        return DeltaProjectDataEntity(articles.take(limit))
    }
}