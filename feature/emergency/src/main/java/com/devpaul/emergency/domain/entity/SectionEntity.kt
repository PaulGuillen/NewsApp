package com.devpaul.emergency.domain.entity

data class SectionEntity(
    val status: Int,
    val data: List<SectionItemEntity>
)

data class SectionItemEntity(
    val title: String,
    val image: String,
    val type: String
)