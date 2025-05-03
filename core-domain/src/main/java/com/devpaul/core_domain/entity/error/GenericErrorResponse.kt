package com.devpaul.core_domain.entity.error

data class ApiErrorResponse(
    val exceptionId: String?,
    val userMessage: String?,
    val exceptionText: String?,
    val moreInfo: String?,
    val httpStatus: Int?,
    val exceptionDetails: List<ExceptionDetail>?,
)

data class ExceptionDetail(
    val code: String?,
    val component: String?,
    val description: String?,
)