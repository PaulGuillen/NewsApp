package com.devpaul.shared.ui.components.organisms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
 * Layout base reutilizable para pantallas que requieren estructura común:
 * encabezado (header), contenido (body), pie de página (footer) y botón flotante (FAB).
 *
 * @param modifier Permite extender o modificar el comportamiento visual externo del layout.
 * @param isBodyScrollable Si es true, el body se hace scrollable verticalmente.
 * @param header Composable opcional que se mostrará en la parte superior (topBar).
 * @param body Composable principal con el contenido central de la pantalla.
 * @param footer Composable opcional que se mostrará en la parte inferior (bottomBar).
 * @param floatingActionButton FAB opcional que se posiciona sobre el contenido principal.
 * @param applyBottomPaddingWhenNoFooter Si no se usa footer, aplicar padding inferior para evitar superposición con nav bar.
 */

@Composable
fun BaseContentLayout(
    modifier: Modifier = Modifier,
    isBodyScrollable: Boolean = true,
    header: @Composable (() -> Unit)? = null,
    body: @Composable () -> Unit,
    footer: @Composable (() -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    applyBottomPaddingWhenNoFooter: Boolean = false
) {
    // Scroll state para el contenido si se permite scroll
    val scrollState = rememberScrollState()

    // Configuración del dispositivo para detectar orientación
    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    // Padding horizontal condicional: más margen en modo landscape
    val horizontalPadding = if (isPortrait) 0.dp else 48.dp

    // Scaffold: estructura básica con topBar, content, bottomBar y FAB
    Scaffold(
        modifier = modifier.fillMaxSize(),

        // Encabezado
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

        // Pie de página (footer)
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

        // Botón flotante (FAB)
        floatingActionButton = {
            floatingActionButton?.invoke()
        }
    ) { innerPadding ->
        // Contenido principal (body)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    // Evita que el contenido quede debajo del header o footer
                    top = if (header != null) innerPadding.calculateTopPadding() else 0.dp,
                    bottom = if (footer != null) innerPadding.calculateBottomPadding() else 0.dp,
                    start = horizontalPadding,
                    end = horizontalPadding
                )
                // Aplica scroll si es necesario
                .then(if (isBodyScrollable) Modifier.verticalScroll(scrollState) else Modifier)
                // Si no hay footer pero se quiere margen inferior, aplica padding
                .then(
                    if (footer == null && applyBottomPaddingWhenNoFooter) Modifier.navigationBarsPadding()
                    else Modifier
                )
                .imePadding(), // Evita que el teclado tape el contenido
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            body()
        }
    }
}