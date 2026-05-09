package com.devpaul.shared.data.datasource

/**
 * Modelo mínimo para persistir noticias guardadas localmente.
 */
data class SavedNews(
    val id: String,
    val title: String,
    val country: String? = null,
    val source: String? = null,
    val category: String?,
    val time: String?,
    val url: String?,
    val isRead: Boolean = false,
    val savedAtMillis: Long = System.currentTimeMillis()
)

// Helper simple para previews / logs: muestra el campo time o un texto vacío
fun SavedNews.displayTime(): String = time ?: ""