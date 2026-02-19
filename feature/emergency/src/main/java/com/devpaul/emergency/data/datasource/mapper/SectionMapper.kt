package com.devpaul.emergency.data.datasource.mapper

import com.devpaul.emergency.domain.entity.SectionEntity
import com.devpaul.emergency.domain.entity.SectionItemEntity
import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.toSectionEntity(): SectionEntity {

    val rawList = get("data") as? List<*> ?: emptyList<Any>()
    val data = rawList.mapNotNull { item ->

        val map = item as? Map<*, *> ?: return@mapNotNull null

        val title = map["title"] as? String
        val image = map["image"] as? String
        val type = map["type"] as? String

        if (title.isNullOrBlank() || image.isNullOrBlank() || type.isNullOrBlank())
            null
        else
            SectionItemEntity(
                title = title,
                image = image,
                type = type
            )
    }

    return SectionEntity(
        status = 200,
        data = data
    )
}