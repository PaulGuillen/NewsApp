package com.devpaul.core_data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.devpaul.core_data.exception.NetworkErrorParcelable
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.Output
import com.devpaul.core_domain.entity.error.ApiErrorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException
import kotlin.reflect.KClass

typealias DefaultOutputWithError<T, E> = Output<T, Defaults<E>>
typealias DefaultOutput<T> = Output<T, Defaults<String>>

suspend inline fun <T : Any> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    noinline apiCall: suspend () -> Response<T>
): DefaultOutput<T> {
    return safeApiCallWithError(
        dispatcher = dispatcher,
        errorKClass = String::class,
        apiCall = apiCall
    )
}

suspend fun <T : Any, E : Any> safeApiCallWithError(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    errorKClass: KClass<E>,
    apiCall: suspend () -> Response<T>
): DefaultOutputWithError<T, E> {
    return withContext(dispatcher) {
        try {
            Timber.d("SafeApiCall - Starting API call")
            val result: Response<T> = apiCall()
            if (result.isSuccessful) {
                Timber.d("SafeApiCall - API call successful")
                Output.Success(result.body()!!)
            } else {
                Timber.e("SafeApiCall - API call failed with HTTP error code: ${result.code()}")
                val errorBody = result.errorBody()?.stringSuspending().orEmpty()
                if (errorBody.isEmpty()) {
                    Timber.e("SafeApiCall - Error body is empty, returning generic error")
                    return@withContext Output.Failure(
                        error = Defaults.HttpError(
                            code = result.code(),
                            data = "" as E,
                            throwable = null
                        )
                    )
                }
                val errorData = createApiError(
                    errorKClass = errorKClass,
                    strError = errorBody
                )

                val apiErrorResponse = Gson().fromJson<ApiErrorResponse>(
                    errorBody,
                    object : TypeToken<ApiErrorResponse>() {}.type
                )

                Output.Failure(
                    error = Defaults.HttpError(
                        code = result.code(),
                        data = errorData,
                        apiErrorResponse = apiErrorResponse,
                        throwable = null
                    )
                )
            }
        } catch (ex: Exception) {
            Timber.e(ex, "SafeApiCall - safeApiCallWithError caught an exception")
            if (ex is NetworkErrorParcelable) {
                return@withContext ex.toDataError()
            }
            Output.Failure(
                error = when (ex) {
                    is HttpException -> {
                        val errorBody = ex.response()?.errorBody()?.stringSuspending().orEmpty()
                        Timber.e("SafeApiCall - HttpException encountered: ${ex.message()}")
                        if (errorBody.isEmpty()) {
                            Timber.e("SafeApiCall - Error body empty during HttpException")
                            return@withContext Output.Failure(
                                error = Defaults.HttpError(
                                    code = ex.code(),
                                    data = "" as E,
                                    throwable = null
                                )
                            )
                        }
                        val errorData = createApiError(
                            errorKClass = errorKClass,
                            strError = errorBody
                        )

                        val apiErrorResponse = Gson().fromJson<ApiErrorResponse>(
                            errorBody,
                            object : TypeToken<ApiErrorResponse>() {}.type
                        )

                        Defaults.HttpError(
                            code = ex.code(),
                            data = errorData,
                            apiErrorResponse = apiErrorResponse,
                            throwable = ex
                        )
                    }

                    is SocketTimeoutException -> {
                        Timber.e("SafeApiCall - SocketTimeoutException encountered")
                        Defaults.TimeoutError(throwable = ex)
                    }

                    else -> {
                        Timber.e("SafeApiCall - Unknown exception type encountered")
                        val errorCode = -1
                        Defaults.UnknownError(
                            code = errorCode,
                            throwable = ex
                        )
                    }
                }
            )
        }
    }
}

suspend inline fun <T : Any, reified E : Any> safeApiCallWithError(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    noinline apiCall: suspend () -> Response<T>
): DefaultOutputWithError<T, E> {
    return safeApiCallWithError(
        dispatcher = dispatcher,
        errorKClass = E::class,
        apiCall = apiCall
    )
}

@Suppress("UNCHECKED_CAST")
private fun <T : Any> createApiError(
    errorKClass: KClass<T>,
    strError: String
): T {
    return when (errorKClass) {
        String::class -> return strError as T
        Unit::class -> Unit as T
        else -> Gson().fromJson(strError, errorKClass.java)
    }
}

suspend fun ResponseBody.stringSuspending() = try {
    withContext(Dispatchers.IO) { string() }
} catch (_: Exception) {
    null
}