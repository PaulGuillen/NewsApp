package com.devpaul.auth.data.datasource.dto.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("lastname")
    val lastName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("birthdate")
    val birthdate: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)