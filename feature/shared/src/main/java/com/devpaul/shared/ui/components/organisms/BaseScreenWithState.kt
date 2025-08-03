package com.devpaul.shared.ui.components.organisms

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.shared.ui.components.molecules.CenteredSnackBarHost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun <UiState, UiIntent, UiEvent> BaseScreenWithState(
    viewModel: StatefulViewModel<UiState, UiIntent, UiEvent>, // ViewModel que maneja estado y eventos.
    navController: NavHostController, // Controlador de navegación.

    // Acción inicial (como una llamada de carga) que se ejecuta al iniciar la pantalla.
    onInit: (suspend (uiState: UiState, onIntent: (UiIntent) -> Unit) -> Unit)? = null,

    // Manejador de eventos unidireccionales (ej: mostrar SnackBar, navegación, etc).
    onUiEvent: suspend (UiEvent, (String) -> Unit) -> Unit = { _, _ -> },

    // Manejador de errores por defecto si se usa ResultState<Defaults>.
    onDefaultError: (Defaults<Nothing>, (String) -> Unit) -> Unit = { _, _ -> },

    // Callback opcional para observar eventos del ciclo de vida (onStart, onStop, etc).
    lifecycle: ((Lifecycle.Event) -> Unit)? = null,

    // Claves para observar valores de retorno del backstack (ej. resultados de navegación).
    observeBackKeys: List<String>? = null,

    // Manejador para procesar resultados recibidos del backstack.
    onBackResults: ((results: Map<String, Any?>, uiState: UiState, onIntent: (UiIntent) -> Unit) -> Unit)? = null,

    // Manejador personalizado para interceptar el botón de retroceso del sistema.
    onBackPressed: ((onBackWithResult: (String, Any) -> Unit) -> Unit)? = null,

    // Composable de contenido que recibe el estado y funciones clave.
    content: @Composable (
        padding: PaddingValues,
        uiState: UiState,
        onIntent: (UiIntent) -> Unit,
        showSnackBar: (String) -> Unit,
        onBackWithResult: (String, Any) -> Unit
    ) -> Unit
) {
    // Host para Snackbars.
    val snackBarHostState = remember { SnackbarHostState() }

    // Ámbito para correr coroutines UI.
    val coroutineScope = rememberCoroutineScope()

    // Observación del estado del ViewModel como State.
    val uiState by viewModel.uiStateFlow.collectAsState()

    // Referencia al lifecycle owner actual (ej. Activity o NavBackStack).
    val lifecycleOwner = LocalLifecycleOwner.current

    // Función para mostrar mensajes snackbar.
    val showSnackBar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }

    // Función para enviar intents al ViewModel.
    val executeIntent: (UiIntent) -> Unit = { viewModel.executeUiIntent(it) }

    // Función para volver atrás con resultado y pasarlo al backstack anterior.
    val onBackWithResult: (String, Any) -> Unit = { key, value ->
        navController.previousBackStackEntry?.savedStateHandle?.set(key, value)
        navController.popBackStack()
    }

    // Observador del ciclo de vida si el parámetro `lifecycle` está definido.
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle?.invoke(event)
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Interceptor del botón físico de retroceso si se define `onBackPressed`.
    onBackPressed?.let { handler ->
        BackHandler {
            handler(onBackWithResult)
        }
    }

    // Efecto que se lanza una única vez para ejecutar la lógica de `onInit`.
    LaunchedEffect(Unit) {
        onInit?.let {
            withContext(Dispatchers.IO) {
                it(uiState, executeIntent)
            }
        }
    }

    // Recolección de eventos de UI emitidos por el ViewModel.
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            launch {
                onUiEvent(event, showSnackBar)
            }
        }
    }

    // Observación de claves del backstack para recuperar resultados de navegación.
    val backResults: Map<String, Any?> = observeBackKeys
        ?.associateWith { key ->
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.getLiveData<Any>(key)
                ?.observeAsState()
                ?.value
        }
        ?.filterValues { it != null }
        .orEmpty()

    // Limpieza y entrega de resultados al recibir datos del backstack.
    LaunchedEffect(backResults) {
        if (backResults.isNotEmpty()) {
            observeBackKeys?.forEach { key ->
                navController.currentBackStackEntry?.savedStateHandle?.remove<Any>(key)
            }
            onBackResults?.invoke(backResults, uiState, executeIntent)
        }
    }

    // Scaffold para envolver el contenido de la pantalla.
    Scaffold(
        snackbarHost = { CenteredSnackBarHost(hostState = snackBarHostState) },
        containerColor = Color.Transparent
    ) { padding ->
        content(padding, uiState, executeIntent, showSnackBar, onBackWithResult)
    }
}

@Composable
fun <T> OnValueChangeEffect(value: T?, onChange: (T) -> Unit) {
    // Mantiene la referencia más reciente al valor observado.
    val currentValue = rememberUpdatedState(value)

    // Se lanza cuando `value` cambia.
    LaunchedEffect(currentValue.value) {
        currentValue.value?.let(onChange)
    }
}

fun NavHostController.setNavigationResult(key: String, value: Any) {
    this.previousBackStackEntry?.savedStateHandle?.set(key, value)
}