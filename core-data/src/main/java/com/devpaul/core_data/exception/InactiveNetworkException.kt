package com.devpaul.core_data.exception

import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.Output

class InactiveNetworkException: Exception(), NetworkErrorParcelable {

    override val message: String
        get() = "Network is not active"

    override fun <I, E> toDataError(): Output<I, Defaults<E>> {
        return Output.Failure(
            error = Defaults.InactiveNetworkError(
                throwable = this
            )
        )
    }
}