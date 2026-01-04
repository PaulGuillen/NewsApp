package com.devpaul.home.data.datasource.mapper

import com.devpaul.home.domain.entity.GratitudeDataEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.google.firebase.firestore.DocumentSnapshot

fun List<DocumentSnapshot>.toGratitudeEntity(): GratitudeEntity {

    val data = mapNotNull { doc ->

        val title = doc.getString("title")
        val imageUrl = doc.getString("image")
        val link = doc.getString("url")

        if (
            title.isNullOrBlank() ||
            imageUrl.isNullOrBlank() ||
            link.isNullOrBlank()
        ) return@mapNotNull null

        GratitudeDataEntity(
            id = doc.id,
            imageUrl = imageUrl,
            title = title,
            link = link
        )
    }

    return GratitudeEntity(
        status = 200,
        message = "OK",
        data = data
    )
}