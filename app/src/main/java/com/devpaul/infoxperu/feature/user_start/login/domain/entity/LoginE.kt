package com.devpaul.infoxperu.feature.user_start.login.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginE(
    val status: Int,
    val message: String,
    val uid: String?,
) : Parcelable
