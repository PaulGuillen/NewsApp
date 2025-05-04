package com.devpaul.core_platform.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun ViewModel.launch(
    context: CoroutineContext = Dispatchers.Main,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(
        context = context,
        start = start,
        block = block
    )
}

inline fun <E> StatefulViewModel<E, *, *>.setUiState(update: E.() -> E) {
    setUiState(update(uiState))
}