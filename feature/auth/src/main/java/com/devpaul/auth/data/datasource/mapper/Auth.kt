package com.devpaul.auth.data.datasource.mapper

import com.devpaul.auth.domain.entity.Auth
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toDomain(): Auth {
    return Auth(
        uid = uid,
        email = email.orEmpty(),
        name = displayName.orEmpty(),
    )
}