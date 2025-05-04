package com.devpaul.core_platform.entity

interface Communicable {
    val tag: String
}

interface DialogInteractionListener {
    fun onDialogAccept(tag: String?)

    fun onDialogCancel(tag: String?)
}