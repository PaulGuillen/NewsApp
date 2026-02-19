package com.devpaul.emergency.data.datasource.mapper

import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.entity.GeneralEntityItem
import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.toGeneralEntity(): GeneralEntity {

    val rawList = get("data") as? List<*> ?: emptyList<Any>()
    val data = rawList.mapNotNull { item ->

        val map = item as? Map<*, *> ?: return@mapNotNull null

        val key = map["key"] as? String
        val value = map["value"] as? List<*> ?: return@mapNotNull null
        val valueList = value.filterIsInstance<String>()

        if (key.isNullOrBlank() || valueList.isEmpty()) {
            null
        } else {
            GeneralEntityItem(
                key = key,
                value = valueList
            )
        }
    }

    return GeneralEntity(
        status = 200,
        data = data
    )
}