package com.devpaul.news.data.datasource.remote

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.core_domain.entity.error.ApiException
import com.devpaul.core_domain.entity.transform
import com.devpaul.infoxperu.domain.models.res.RedditResponse
import com.devpaul.news.data.datasource.dto.res.GDELTResponse
import com.devpaul.news.data.datasource.mapper.toCountryEntity
import com.devpaul.news.data.datasource.mapper.toDomain
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Factory
import timber.log.Timber

@Factory
class NewsServiceDS(
    private val firestore: FirebaseFirestore,
    private val newsService: NewsService,
    private val googleService: GoogleService,
) {

    suspend fun countryService(): CountryEntity {
        val snapshot = firestore
            .collection(COLLECTION_COUNTRY)
            .get()
            .await()

        return snapshot.documents.toCountryEntity()
    }

    suspend fun googleService(
        q: String,
        hl: String,
        page: Int,
        perPage: Int,
    ): DefaultOutput<GoogleEntity> {
        return safeApiCall {
            googleService.google(
                query = q,
                mode = hl,
                page = page,
                perPage = perPage
            )
        }.transform { it.toDomain() }
    }

    suspend fun deltaProjectService(
        query: String,
        mode: String,
        format: String
    ) : GDELTResponse {
        try {
            val response = newsService.deltaProject(query, mode, format)
            if (response.isSuccessful) {
                return response.body()
                    ?: throw Exception("Error fetching data: response body is null")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                throw ApiException(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            Timber.e("Error fetching data: ${e.message}")
            throw e
        }
    }

    suspend fun redditService(country: String): RedditResponse {
        try {
            val response = newsService.redditNews(country)
            if (response.isSuccessful) {
                return response.body()
                    ?: throw Exception("Error fetching data: response body is null")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Timber.e("Error fetching data: $errorMessage")
                throw ApiException(response.code(), errorMessage)

            }
        } catch (e: Exception) {
            Timber.e("Error fetching data: ${e.message}")
            throw e
        }
    }

    companion object {
        private const val COLLECTION_COUNTRY = "country"
    }
}