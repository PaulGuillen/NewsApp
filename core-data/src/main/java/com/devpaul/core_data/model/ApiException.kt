package com.devpaul.core_data.model

class ApiException(val code: Int, message: String) : Exception(message)
