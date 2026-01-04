package com.devpaul.home.data.datasource.mapper

import com.devpaul.home.domain.entity.SectionDataEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.google.firebase.firestore.DocumentSnapshot

fun List<DocumentSnapshot>.toSectionEntity(): SectionEntity {

    val data = mapNotNull { doc ->

        val title = doc.getString("title")
        val type = doc.getString("type")

        if (title.isNullOrBlank() || type.isNullOrBlank()) return@mapNotNull null

        SectionDataEntity(
            id = doc.id,
            title = title,
            type = type,
            imageUrl = doc.getString("imageUrl").orEmpty(),
            description = doc.getString("description").orEmpty(),
            destination = doc.getString("destination").orEmpty()
        )
    }

    return SectionEntity(
        status = 200,
        message = "OK",
        data = data
    )
}
