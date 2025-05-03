package com.devpaul.core_domain.entity

sealed class Output<out T, out E> {

    data class Success<T>(
        val data: T
    ) : Output<T, Nothing>()

    open class Failure<E>(
        val error: E
    ) : Output<Nothing, E>()

    inline fun onSuccessful(block: (T) -> Unit): Output<T, E> {
        if (this.isSuccessful()) {
            block(data)
        }
        return this
    }

    inline fun onFailure(block: (E) -> Unit): Output<T, E> {
        if (this.isFailure()) {
            block(error)
        }
        return this
    }

    @JvmName("onSpecificFailure")
    inline fun <reified O> onFailure(block: (O) -> Unit): Output<T, E> {
        onFailure {
            if (it is O) {
                block(it)
            }
        }
        return this
    }
}