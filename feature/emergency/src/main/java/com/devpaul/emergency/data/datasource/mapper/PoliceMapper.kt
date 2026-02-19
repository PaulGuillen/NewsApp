package com.devpaul.emergency.data.datasource.mapper

import com.devpaul.emergency.domain.entity.PoliceEntity
import com.devpaul.emergency.domain.entity.PoliceEntityItem
import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.toPoliceEntity(): PoliceEntity {

    val rawList = get("data") as? List<*> ?: emptyList<Any>()
    val data = rawList.mapNotNull { item ->

        val map = item as? Map<*, *> ?: return@mapNotNull null

        val address = map["address"] as? String
        val district = map["district"] as? String
        val email = map["email"] as? String
        val phone = map["phone"] as? String
        val unit = map["unit"] as? String

        if (address.isNullOrBlank() || district.isNullOrBlank()
            || email.isNullOrBlank() || phone.isNullOrBlank()
            || unit.isNullOrBlank()
        ) {
            null
        } else {
            PoliceEntityItem(
                address = address,
                district = district,
                email = email,
                phone = phone,
                unit = unit,
            )
        }
    }

    return PoliceEntity(
        status = 200,
        data = data
    )
}