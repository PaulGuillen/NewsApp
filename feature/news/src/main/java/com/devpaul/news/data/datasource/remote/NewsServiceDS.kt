package com.devpaul.news.data.datasource.remote

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.news.domain.entity.CountryEntity
import org.koin.core.annotation.Factory
import com.devpaul.core_domain.entity.transform
import com.devpaul.news.data.datasource.mapper.toDomain
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity

@Factory
class NewsServiceDS(
    private val newsService: NewsService,
) {
    suspend fun countryService(): DefaultOutput<CountryEntity> {
        return safeApiCall {
            newsService.country()
        }.transform { it.toDomain() }
    }

    suspend fun googleService(
        q: String,
        hl: String,
        page: Int,
        perPage: Int,
    ): DefaultOutput<GoogleEntity> {
        return safeApiCall {
            newsService.google(
                query = q,
                mode = hl,
                page = page,
                perPage = perPage
            )
        }.transform { it.toDomain() }
    }

    suspend fun deltaProjectService(
        q: String,
        mode: String,
        format: String,
        page: Int,
        perPage: Int,
    ): DefaultOutput<DeltaProjectEntity> {
        return safeApiCall {
            newsService.deltaProject(
                query = q,
                mode = mode,
                format = format,
                page = page,
                perPage = perPage,
            )
        }.transform { it.toDomain() }
    }

    suspend fun redditService(
        country: String,
        page: Int,
        perPage: Int,
    ): DefaultOutput<RedditEntity> {
        return safeApiCall {
            newsService.reddit(
                country = country,
                page = page,
                perPage = perPage
            )
        }.transform { it.toDomain() }
    }

}