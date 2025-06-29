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

abstract class StatelessViewModel<UiIntent, UiEvent> : ViewModel(),
    UiEventHolder<UiEvent>,
    UiIntentHolder<UiIntent>,
    ViewModelLoadable {

    // Canal para recibir intents desde la UI (acciones del usuario o del sistema).
    private val uiIntentChannel = Channel<UiIntent>(Channel.BUFFERED)

    // Canal para enviar errores por defecto (network, timeout, etc.)
    private val defaultsChannel = Channel<Defaults<Nothing>>(Channel.BUFFERED)

    // Flujo compartido para emitir eventos unidireccionales (navegación, toast, dialog, etc.)
    private val _uiEvent = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    // Estado de carga observado por la UI.
    private val _isLoading = MutableStateFlow(false)
    override var isLoading: Boolean
        get() = _isLoading.value
        set(value) {
            _isLoading.tryEmit(value)
        }

    init {
        // Inicia una coroutine que escucha los intents y los procesa con `onUiIntent`
        launch {
            uiIntentChannel.receiveAsFlow().collectLatest {
                onUiIntent(it)
            }
        }
    }

    /**
     * Registra un listener que será llamado cada vez que se emita un evento de UI.
     * Ideal para navegación, mensajes, modales, etc.
     */
    override suspend fun setOnUiEvent(onEvent: suspend (UiEvent) -> Unit) {
        _uiEvent.collectLatest(onEvent)
    }

    /**
     * Registra un listener para los errores predefinidos (network, timeout, etc.).
     * Se usa para desacoplar lógica de manejo de errores comunes.
     */
    suspend fun setOnDefaultError(onDefaultOutput: suspend (Defaults<Nothing>) -> Unit) {
        defaultsChannel.receiveAsFlow().collectLatest(onDefaultOutput)
    }

    /**
     * Emite un evento unidireccional a la UI.
     */
    final override fun UiEvent.send() {
        _uiEvent.tryEmit(this)
    }

    /**
     * Permite a la UI observar los cambios en el estado de carga.
     */
    override suspend fun onLoading(listener: (isLoading: Boolean) -> Unit) {
        _isLoading.collectLatest {
            listener(it)
        }
    }

    /**
     * Ejecuta un intent desde dentro del ViewModel.
     * Lo coloca en el canal para que sea procesado por `onUiIntent`.
     */
    protected fun UiIntent.execute() {
        uiIntentChannel.trySendBlocking(this)
    }

    /**
     * Función pública que permite ejecutar un intent desde la UI.
     */
    override fun executeUiIntent(intent: UiIntent) {
        uiIntentChannel.trySendBlocking(intent)
    }

    /**
     * Manejo de errores estándar para las respuestas de red.
     * Envía errores comunes al canal de errores para que sean tratados globalmente.
     */
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

    /**
     * Lanza una coroutine suspendida en Dispatchers.IO.
     * Útil para llamadas a red o I/O sin bloquear el hilo principal.
     */
    protected fun launchIO(block: suspend () -> Unit) {
        launch {
            withContext(Dispatchers.IO) {
                block()
            }
        }
    }

    /**
     * Ejecuta múltiples operaciones concurrentes.
     * Usa supervisorScope para evitar que una excepción cancele las otras.
     */
    suspend fun launchConcurrentRequests(vararg requests: suspend () -> Unit) {
        supervisorScope {
            requests.forEach { request ->
                launch {
                    try {
                        request()
                    } catch (e: CancellationException) {
                        throw e // respetar cancelación estructurada
                    } catch (e: Exception) {
                        Timber.e("ConcurrentRequests", "Request failed", e)
                    }
                }
            }
        }
    }
}