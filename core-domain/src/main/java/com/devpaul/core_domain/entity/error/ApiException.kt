package com.devpaul.core_domain.entity.error

class ApiException(val code: Int, message: String) : Exception(message)