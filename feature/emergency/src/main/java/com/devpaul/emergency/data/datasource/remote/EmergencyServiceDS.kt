package com.devpaul.emergency.data.datasource.remote

import com.devpaul.emergency.data.datasource.mapper.toGeneralEntity
import com.devpaul.emergency.data.datasource.mapper.toSectionEntity
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.entity.SectionEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Factory

@Factory
class EmergencyServiceDS(
    private val firestore: FirebaseFirestore,
    private val emergencyService: EmergencyService,
) {

    suspend fun sectionService(): SectionEntity {
        val snapshot = firestore
            .collection(COLLECTION_DISTRICT)
            .document(COLLECTION_SECTION)
            .get()
            .await()

        return snapshot.toSectionEntity()
    }

    suspend fun generalService(): GeneralEntity {
        val snapshot = firestore
            .collection(COLLECTION_DISTRICT)
            .document(COLLECTION_GENERAL)
            .get()
            .await()

        return snapshot.toGeneralEntity()
    }

    suspend fun civilDefenseService(): GeneralEntity {
        val snapshot = firestore
            .collection(COLLECTION_DISTRICT)
            .document(COLLECTION_DEFENSE_CIVIL)
            .get()
            .await()

        return snapshot.toGeneralEntity()
    }

    companion object {
        private const val COLLECTION_DISTRICT = "district"
        private const val COLLECTION_SECTION = "section"
        private const val COLLECTION_GENERAL = "general"
        private const val COLLECTION_DEFENSE_CIVIL = "civil_defense"
    }
}