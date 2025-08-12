package com.devpaul.emergency.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val data: List<GeneralResponseItem>,
    @SerializedName("pagination")
    val pagination: PaginationResponse
)

data class GeneralResponseItem(
    @SerializedName("key")
    val key: String,
    @SerializedName("value")
    val value: List<String>
)

data class PaginationResponse(
    @SerializedName("total")
    val total: Int,
    @SerializedName("perPage")
    val perPage: Int,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)