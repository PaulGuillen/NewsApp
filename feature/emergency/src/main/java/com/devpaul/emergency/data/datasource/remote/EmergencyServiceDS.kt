package com.devpaul.emergency.data.datasource.remote

import com.devpaul.core_data.util.Constant
import com.devpaul.emergency.data.datasource.mapper.toGeneralEntity
import com.devpaul.emergency.data.datasource.mapper.toPoliceEntity
import com.devpaul.emergency.data.datasource.mapper.toSectionEntity
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.entity.PoliceEntity
import com.devpaul.emergency.domain.entity.SectionEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Factory

@Factory
class EmergencyServiceDS(
    private val firestore: FirebaseFirestore,
) {

    suspend fun sectionService(): SectionEntity {
        val snapshot = firestore
            .collection(Constant.COLLECTION_DISTRICT)
            .document(Constant.COLLECTION_SECTION)
            .get()
            .await()

        return snapshot.toSectionEntity()
    }

    suspend fun generalService(): GeneralEntity {
        val snapshot = firestore
            .collection(Constant.COLLECTION_DISTRICT)
            .document(Constant.COLLECTION_GENERAL)
            .get()
            .await()

        return snapshot.toGeneralEntity()
    }

    suspend fun policeService(type: String): PoliceEntity {
        val snapshot = firestore
            .collection(Constant.COLLECTION_DISTRICT)
            .document(type)
            .get()
            .await()

        return snapshot.toPoliceEntity()
    }

    suspend fun civilDefenseService(): GeneralEntity {
        val snapshot = firestore
            .collection(Constant.COLLECTION_DISTRICT)
            .document(Constant.COLLECTION_DEFENSE_CIVIL)
            .get()
            .await()

        return snapshot.toGeneralEntity()
    }

}