package com.devpaul.infoxperu.feature.auth.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecoveryPasswordE(
    val status: Int,
    val message: String,
) : Parcelable
