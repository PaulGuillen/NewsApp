package com.devpaul.news.domain.usecase

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.transform
import com.devpaul.core_domain.entity.transformHttpError
import com.devpaul.core_domain.use_case.SimpleUC
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.NewsItemJSON
import com.devpaul.news.domain.repository.NewsRepository
import org.koin.core.annotation.Factory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Factory
class GoogleUC(
    private val newsRepository: NewsRepository,
) : SimpleUC.ParamsAndResult<GoogleUC.Params, DefaultOutput<GoogleUC.Success>> {

    override suspend fun invoke(params: Params): DefaultOutput<Success> {
        return newsRepository.googleService(
            q = params.q,
            hl = params.hl,
        )
            .transformHttpError {
                Failure.GoogleError(it)
            }
            .transform { googleEntity ->

                val processedItems = googleEntity.data.newsItems
                    .sortedByDescending { it.parsedDateMillis() }
                    .map { it.withSpanishDate() }

                val processed = googleEntity.copy(
                    data = googleEntity.data.copy(
                        newsItems = processedItems
                    )
                )

                Success.GoogleSuccess(processed)
            }
    }

    private fun NewsItemJSON.parsedDateMillis(): Long {
        val parser = SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss z",
            Locale.ENGLISH
        ).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }

        return runCatching {
            parser.parse(this.pubDate ?: "")?.time
        }.getOrDefault(0L) ?: 0L
    }

    private fun NewsItemJSON.withSpanishDate(): NewsItemJSON {
        val formatter = SimpleDateFormat(
            "dd 'de' MMMM yyyy, HH:mm",
            Locale("es", "PE")
        ).apply {
            timeZone = TimeZone.getTimeZone("America/Lima")
        }

        val millis = parsedDateMillis()

        return this.copy(
            pubDate = if (millis == 0L) {
                this.pubDate
            } else {
                formatter.format(Date(millis))
            }
        )
    }

    data class Params(
        val q: String,
        val hl: String,
    )

    sealed class Failure : Defaults.CustomError() {
        data class GoogleError(val error: HttpError<String>) : Failure()
    }

    sealed class Success {
        data class GoogleSuccess(val google: GoogleEntity) : Success()
    }
}