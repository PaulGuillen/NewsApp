package com.devpaul.auth.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Register(
    val status: Int,
    val message: String,
    val uid: String,
) : Parcelable