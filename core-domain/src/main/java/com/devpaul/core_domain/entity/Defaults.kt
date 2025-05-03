package com.devpaul.core_domain.entity

import com.devpaul.core_domain.entity.error.ApiErrorResponse

sealed class Defaults<out E> {

    class HttpError<E>(
        val code: Int,
        val data: E,
        val apiErrorResponse: ApiErrorResponse? = null,
        val throwable: Throwable? = null,
    ): Defaults<E>()

    class InactiveNetworkError(
        val throwable: Throwable
    ): Defaults<Nothing>()

    class TimeoutError(
        val throwable: Throwable
    ): Defaults<Nothing>()

    class ServerError(
        val throwable: Throwable?
    ): Defaults<Nothing>()

    class UnknownError(
        val code: Int,
        val throwable: Throwable
    ): Defaults<Nothing>()

    abstract class CustomError: Defaults<Nothing>()
}