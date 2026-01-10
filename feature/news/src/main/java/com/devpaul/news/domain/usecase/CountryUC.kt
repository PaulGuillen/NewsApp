package com.devpaul.news.domain.usecase

import com.devpaul.core_domain.entity.Output
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.domain.repository.NewsRepository
import org.koin.core.annotation.Factory

@Factory
class CountryUC(
    private val newsRepository: NewsRepository,
) {
    suspend fun countryService(): Output<CountryEntity, Throwable> {
        return try {
            val countryEntity = newsRepository.countryService()

            fun priority(item: CountryItemEntity): Int =
                when (item.title.lowercase()) {
                    "perú", "peru" -> 1
                    "argentina" -> 2
                    "méxico", "mexico" -> 3
                    else -> 4
                }

            val sorted = countryEntity.data
                .sortedWith(
                    compareBy<CountryItemEntity> { priority(it) }
                        .thenBy { it.title }
                )

            Output.Success(
                countryEntity.copy(data = sorted)
            )
        } catch (ex: Exception) {
            Output.Failure(ex)
        }
    }
}