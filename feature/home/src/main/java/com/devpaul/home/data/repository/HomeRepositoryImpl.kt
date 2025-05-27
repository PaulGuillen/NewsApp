package com.devpaul.home.data.repository

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.model.GDELProject
import com.devpaul.core_data.model.GoogleNewsXML
import com.devpaul.core_data.model.NewsResponse
import com.devpaul.core_data.model.RedditResponse
import com.devpaul.home.data.datasource.remote.HomeServiceDS
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.entity.UITEntity
import com.devpaul.home.domain.repository.HomeRepository
import org.koin.core.annotation.Factory

@Factory([HomeRepository::class])
class HomeRepositoryImpl(
    private val serviceDS: HomeServiceDS
) : HomeRepository {

    override suspend fun dollarQuoteService(): DefaultOutput<DollarQuoteEntity> {
        return serviceDS.dollarQuoteService()
    }

    override suspend fun uitService(): DefaultOutput<UITEntity> {
        return serviceDS.uitService()
    }

    override suspend fun sectionService(): DefaultOutput<SectionEntity> {
        return serviceDS.sectionService()
    }

    override suspend fun gratitudeService(): DefaultOutput<GratitudeEntity> {
        return serviceDS.gratitudeService()
    }

    override suspend fun googleNews(query: String, language: String): GoogleNewsXML {
        return serviceDS.googleNews(query, language)
    }

    override suspend fun deltaProject(query: String, mode: String, format: String): GDELProject {
        return serviceDS.deltaProject(query, mode, format)
    }

    override suspend fun redditNews(country: String): RedditResponse {
        return serviceDS.redditNews(country)
    }

    override suspend fun newsAPI(initLetters: String): NewsResponse {
        return serviceDS.newsAPI(initLetters)
    }
}