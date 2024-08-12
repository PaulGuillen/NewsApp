package com.devpaul.infoxperu.domain.models.res

class ApiException(val code: Int, message: String) : Exception(message)
