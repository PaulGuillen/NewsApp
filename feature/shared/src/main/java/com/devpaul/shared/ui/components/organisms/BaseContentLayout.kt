package com.devpaul.shared.ui.components.organisms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

/**
 * Layout base reutilizable para pantallas que requieren una estructura con:
 * - Header (parte superior),
 * - Body (contenido principal),
 * - Footer (parte inferior),
 * - FAB (Floating Action Button).
 *
 * Puedes controlar si quieres aplicar paddings para el teclado, status bar o navigation bar.
 *
 * @param modifier Modificador externo para ajustar el diseño general.
 * @param isBodyScrollable Define si el contenido principal debe ser desplazable verticalmente.
 * @param applyImePadding Aplica padding inferior si el teclado está activo.
 * @param applyStatusBarsPaddingToHeader Aplica padding superior al header para evitar solaparse con la status bar.
 * @param applyNavigationBarsPaddingToFooter Aplica padding inferior al footer para evitar solaparse con la navigation bar.
 * @param header Composable opcional para la parte superior (topBar).
 * @param body Composable principal con el contenido central.
 * @param footer Composable opcional para la parte inferior (bottomBar).
 * @param floatingActionButton Composable opcional para un botón flotante.
 * @param applyBottomPaddingWhenNoFooter Si no hay footer, aplica padding inferior para evitar solaparse con la navigation bar.
 */
@Composable
fun BaseContentLayout(
    modifier: Modifier = Modifier,
    isBodyScrollable: Boolean = true,
    applyImePadding: Boolean = true,
    applyStatusBarsPaddingToHeader: Boolean = true,
    applyNavigationBarsPaddingToFooter: Boolean = true,
    header: @Composable (() -> Unit)? = null,
    body: @Composable () -> Unit,
    footer: @Composable (() -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    applyBottomPaddingWhenNoFooter: Boolean = false
) {
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT


    if (isPortrait) {
        Scaffold(
            modifier = modifier.fillMaxSize(),

            // Header
            topBar = {
                header?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .statusBarsPadding(), // Evita solaparse con status bar
                        contentAlignment = Alignment.Center
                    ) {
                        it()
                    }
                }
            },

            // Footer
            bottomBar = {
                footer?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .navigationBarsPadding(), // Evita solaparse con sistema de navegación
                        contentAlignment = Alignment.Center
                    ) {
                        it()
                    }
                }
            },

            // FAB
            floatingActionButton = {
                floatingActionButton?.invoke()
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    // Padding según si hay header/footer
                    .padding(
                        top = if (header != null && applyStatusBarsPaddingToHeader) innerPadding.calculateTopPadding() else 0.dp,
                        bottom = if (footer != null && applyNavigationBarsPaddingToFooter) innerPadding.calculateBottomPadding() else 0.dp,
                        start = 0.dp,
                        end = 0.dp
                    )
                    // Scroll si se requiere
                    .then(if (isBodyScrollable) Modifier.verticalScroll(scrollState) else Modifier)
                    // Padding inferior si no hay footer pero se requiere espacio para navegación
                    .then(
                        if (footer == null && applyBottomPaddingWhenNoFooter)
                            Modifier.navigationBarsPadding()
                        else Modifier
                    )
                    // Padding por teclado si está activado
                    .then(if (applyImePadding) Modifier.imePadding() else Modifier),
                    // Por seguridad en orientación vertical, llena altura
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Contenido del body alineado arriba y centrado horizontalmente
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    body()
                }
            }
        }
    } else {
        Scaffold(
            modifier = modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding(),
            // Header
            topBar = {
                header?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .statusBarsPadding(), // Evita solaparse con status bar
                        contentAlignment = Alignment.Center
                    ) {
                        it()
                    }
                }
            },

            // Footer
            bottomBar = {
                footer?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .navigationBarsPadding(), // Evita solaparse con sistema de navegación
                        contentAlignment = Alignment.Center
                    ) {
                        it()
                    }
                }
            },

            // FAB
            floatingActionButton = {
                floatingActionButton?.invoke()
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()

                    // Padding según si hay header/footer
                    .padding(
                        top = if (header != null && applyStatusBarsPaddingToHeader) innerPadding.calculateTopPadding() else 0.dp,
                        bottom = if (footer != null && applyNavigationBarsPaddingToFooter) innerPadding.calculateBottomPadding() else 0.dp,
                        start = 0.dp,
                        end = 0.dp
                    )
                    // Scroll si se requiere
                    .then(if (isBodyScrollable) Modifier.verticalScroll(scrollState) else Modifier)
                    // Padding por teclado si está activado
                    .then(if (applyImePadding) Modifier.imePadding() else Modifier),
            ) {
                // Contenido del body alineado arriba y centrado horizontalmente
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    body()
                }
            }
        }
    }

}