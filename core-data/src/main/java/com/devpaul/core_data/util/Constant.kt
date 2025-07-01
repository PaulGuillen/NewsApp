package com.devpaul.core_data.util

object Constant {
    // DataStore Keys
    const val LOG_IN_KEY = "logIn"
    const val USER_UID_KEY = "userUID"

    //Collections
    const val USERS_COLLECTION = "users"
    const val COUNTRY_COLLECTION = "country"

    // Fields
    const val ID_FIELD = "id"
    const val NAME_FIELD = "name"
    const val LAST_NAME_FIELD = "lastName"
    const val EMAIL_FIELD = "email"
    const val PASSWORD_FIELD = "password"

    // Messages
    const val LOGIN_SUCCESS = "Inicio de sesión exitoso"
    const val LOGIN_FAILURE = "Inicio de sesión fallido"
    const val LOGIN_ERROR = "Error en el inicio de sesión: "
    const val PASSWORD_RECOVERY_SUCCESS = "Correo de recuperación enviado"
    const val PASSWORD_RECOVERY_FAILURE = "Error al enviar el correo: "
    const val USER_ALREADY_LOGGED_IN = "Usuario ya autenticado"
    const val REGISTER_SUCCESS = "Registro exitoso"
    const val REGISTER_ERROR = "Error al obtener el ID del usuario"
    const val REGISTER_FAILURE = "Error en el registro: "

    // Other constants
    const val LOGIN_DELAY = 1500L
    const val COUNTRY_PERU = "Perú"
    const val COUNTRY_ARGENTINA = "Argentina"
    const val COUNTRY_MEXICO = "Mexico"

    //Collections
    const val GRATITUDE = "gratitude"
    const val SECTION_ITEMS = "sectionItems"

    // NewsScreen
    const val NEWS_LANGUAGE = "es"
    const val NEWS_MODE = "ArtList"
    const val NEWS_FORMAT = "json"

    //URL IMAGE
    const val URL_IMAGE = "https://img.freepik.com/premium-vector/default-avatar-profile-icon-social-media-user-image-gray-avatar-icon-blank-profile-silhouette-vector-illustration_561158-3407.jpg"
}