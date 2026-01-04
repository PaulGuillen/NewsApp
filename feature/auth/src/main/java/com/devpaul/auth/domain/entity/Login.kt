package com.devpaul.auth.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Auth(
    val uid: String,
    val email: String,
    val name: String,
) : Parcelable