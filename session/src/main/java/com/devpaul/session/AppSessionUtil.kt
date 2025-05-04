package com.devpaul.session

private val InitSessionException = IllegalStateException("Init session first")

internal fun <T: Any> safeSessionValue(block: () -> T?): T {
    return block() ?: throw InitSessionException
}