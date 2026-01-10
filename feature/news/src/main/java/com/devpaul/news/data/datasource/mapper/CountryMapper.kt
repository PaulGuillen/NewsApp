package com.devpaul.news.data.datasource.mapper

import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.CountryItemEntity
import com.google.firebase.firestore.DocumentSnapshot

fun List<DocumentSnapshot>.toCountryEntity(): CountryEntity {

    val data = mapNotNull { doc ->

        val summary = doc.getString("summary")
        val title = doc.getString("title")
        val category = doc.getString("category")
        val imageUrl = doc.getString("imageUrl")
        val initLetters = doc.getString("initLetters")

        if (
            summary.isNullOrBlank() ||
            title.isNullOrBlank() ||
            category.isNullOrBlank() ||
            imageUrl.isNullOrBlank() ||
            initLetters.isNullOrBlank()
        ) return@mapNotNull null

        CountryItemEntity(
            id = doc.id,
            summary = summary,
            title = title,
            category = category,
            imageUrl = imageUrl,
            initLetters = initLetters,
        )
    }

    return CountryEntity(
        status = 200,
        message = "OK",
        data = data
    )
}