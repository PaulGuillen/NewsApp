package com.devpaul.profile.domain.entity

data class ProfileEntity(
    val status : Int,
    val message : String,
    val data : ProfileUserEntity
)

data class ProfileUserEntity(
    val id: String,
    val uid: String,
    val name: String,
    val lastname: String,
    val phone: String,
    val birthdate: String,
    val email: String,
    val password: String,
    val image: String? = null
)