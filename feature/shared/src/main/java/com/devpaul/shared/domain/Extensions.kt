package com.devpaul.shared.domain

import com.devpaul.core_domain.entity.Defaults

fun handleDefaultErrors(
    error: Defaults<Nothing>,
    showSnackBar: (String) -> Unit
) {
    when (error) {
        is Defaults.InactiveNetworkError -> showSnackBar("Sin conexión a internet")
        is Defaults.TimeoutError -> showSnackBar("Tiempo de espera agotado")
        is Defaults.ServerError -> showSnackBar("Error del servidor")
        is Defaults.UnknownError -> showSnackBar("Error desconocido")
        is Defaults.CustomError -> showSnackBar( "Error inesperado")
        is Defaults.HttpError<*> -> showSnackBar("Error HTTP: ${error.code}")
    }
}