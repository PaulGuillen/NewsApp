package com.devpaul.core_domain.entity

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun <T, E> Output<T, E>.isSuccessful(): Boolean {
    contract {
        returns(true) implies (this@isSuccessful is Output.Success)
        returns(false) implies (this@isSuccessful is Output.Failure)
    }
    return this is Output.Success
}

@OptIn(ExperimentalContracts::class)
fun <T, E> Output<T, E>.isFailure(): Boolean {
    contract {
        returns(false) implies (this@isFailure is Output.Success)
        returns(true) implies (this@isFailure is Output.Failure)
    }
    return this !is Output.Success
}

fun <I1, I2, O1> Output<I1, I2>.transform(transformer: (I1)-> O1): Output<O1, I2> {
    return when(this) {
        is Output.Success -> Output.Success(data = transformer(data))
        is Output.Failure -> this
    }
}

fun <I1, I2, O2> Output<I1, I2>.transformFailure(transformer: (I2)-> O2): Output<I1, O2> {
    return when(this) {
        is Output.Success -> this
        is Output.Failure -> Output.Failure(error = transformer(error))
    }
}

fun <I1, I2, O1> Output<List<I1>, I2>.map(mapper: (I1)->O1): Output<List<O1>, I2> {
    return when(this) {
        is Output.Success -> Output.Success(data = data.map(mapper))
        is Output.Failure -> this
    }
}

fun <I, O> Output<I, O>.toUnit(): Output<Unit, O> {
    return this.transform { }
}

fun <I, O> Output<I, Defaults<O>>.useSimpleDefaults(): Output<I, Defaults<O>> {
    if (this is Output.Failure) {
        return if (this.error is Defaults.HttpError<*>) {
            Output.Failure(error = Defaults.ServerError(throwable = this.error.throwable))
        } else {
            this
        }
    }
    return this
}

fun <I, O> Output<I, Defaults<O>>.transformHttpError(transformer: (Defaults.HttpError<O>)-> Defaults<O>): Output<I, Defaults<O>> {
    if (this is Output.Failure) {
        return if (this.error is Defaults.HttpError<O>) {
            val result: Defaults<O> = transformer(this.error)
            if (result is Defaults.HttpError) {
                useSimpleDefaults()
            } else {
                Output.Failure(error = result)
            }
        } else {
            this
        }
    }
    return this
}