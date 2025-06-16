package com.devpaul.core_platform.lifecycle

import androidx.lifecycle.ViewModel
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.Output
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.base.UiEventHolder
import com.devpaul.core_platform.lifecycle.base.UiIntentHolder
import com.devpaul.core_platform.lifecycle.base.ViewModelLoadable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

abstract class StatelessViewModel<UiIntent, UiEvent> : ViewModel(), UiEventHolder<UiEvent>, UiIntentHolder<UiIntent>, ViewModelLoadable {

    private val uiIntentChannel = Channel<UiIntent>(Channel.BUFFERED)
    private val defaultsChannel = Channel<Defaults<Nothing>>(Channel.BUFFERED)

    private val _uiEvent = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    private val _isLoading = MutableStateFlow(false)
    override var isLoading: Boolean
        get() = _isLoading.value
        set(value) {
            _isLoading.tryEmit(value)
        }

    init {
        launch {
            uiIntentChannel.receiveAsFlow().collectLatest {
                onUiIntent(it)
            }
        }
    }

    override suspend fun setOnUiEvent(onEvent: suspend (UiEvent) -> Unit) {
        _uiEvent.collectLatest(onEvent)
    }

    suspend fun setOnDefaultError(onDefaultOutput: suspend (Defaults<Nothing>) -> Unit) {
        defaultsChannel.receiveAsFlow().collectLatest(onDefaultOutput)
    }

    final override fun UiEvent.send() {
        _uiEvent.tryEmit(this)
    }

    override suspend fun onLoading(listener: (isLoading: Boolean) -> Unit) {
        _isLoading.collectLatest {
            listener(it)
        }
    }

    protected fun UiIntent.execute() {
        uiIntentChannel.trySendBlocking(this)
    }

    override fun executeUiIntent(intent: UiIntent) {
        uiIntentChannel.trySendBlocking(intent)
    }

    protected fun <I, E> Output<I, Defaults<E>>.handleNetworkDefault(): Output<I, Defaults<E>> {
        onFailure {
            when (it) {
                is Defaults.InactiveNetworkError -> defaultsChannel.trySendBlocking(it)
                is Defaults.TimeoutError -> defaultsChannel.trySendBlocking(it)
                is Defaults.ServerError -> defaultsChannel.trySendBlocking(it)
                is Defaults.UnknownError -> defaultsChannel.trySendBlocking(it)
                else -> Unit
            }
        }
        return this
    }

    protected fun launchIO(block: suspend () -> Unit) {
        launch {
            withContext(Dispatchers.IO) {
                block()
            }
        }
    }

    suspend fun launchConcurrentRequests(vararg requests: suspend () -> Unit) {
        supervisorScope {
            requests.forEach { request ->
                launch {
                    try {
                        request()
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        Timber.e("ConcurrentRequests", "Request failed", e)
                    }
                }
            }
        }
    }
}