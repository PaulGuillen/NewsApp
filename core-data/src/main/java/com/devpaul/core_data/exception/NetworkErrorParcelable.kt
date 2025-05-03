package com.devpaul.core_data.exception

import com.devpaul.core_domain.entity.Output
import com.devpaul.core_domain.entity.Defaults

interface NetworkErrorParcelable {

    fun <I, E> toDataError(): Output<I, Defaults<E>>

}