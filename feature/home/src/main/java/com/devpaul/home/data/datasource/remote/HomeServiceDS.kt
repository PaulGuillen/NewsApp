package com.devpaul.home.data.datasource.remote

import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.core_domain.entity.transform
import com.devpaul.home.data.datasource.mapper.toDomain
import com.devpaul.home.data.datasource.mapper.toGratitudeEntity
import com.devpaul.home.data.datasource.mapper.toSectionEntity
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.entity.UITEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Factory

@Factory
class HomeServiceDS(
    private val firestore: FirebaseFirestore,
    private val homeService: HomeService,
) {

    suspend fun dollarQuoteService(): DefaultOutput<DollarQuoteEntity> {
        return safeApiCall {
            homeService.dollarQuote()
        }.transform { it.toDomain() }
    }

    suspend fun uitService(): DefaultOutput<UITEntity> {
        return safeApiCall {
            homeService.uit()
        }.transform { it.toDomain() }
    }

    suspend fun sectionService(): SectionEntity {
        val snapshot = firestore
            .collection(COLLECTION_SECTION_ITEMS)
            .get()
            .await()

        return snapshot.documents.toSectionEntity()
    }

    suspend fun gratitudeService(): GratitudeEntity {
        val snapshot = firestore
            .collection(COLLECTION_GRATITUDE)
            .get()
            .await()

        return snapshot.documents.toGratitudeEntity()
    }

    companion object {
        private const val COLLECTION_SECTION_ITEMS = "sectionItems"
        private const val COLLECTION_GRATITUDE = "gratitude"
    }
}