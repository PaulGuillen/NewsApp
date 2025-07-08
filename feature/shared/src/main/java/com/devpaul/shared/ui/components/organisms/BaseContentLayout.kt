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

@Composable
fun BaseContentLayout(
    modifier: Modifier = Modifier,
    isBodyScrollable: Boolean = true,
    header: @Composable (() -> Unit)? = null,
    body: @Composable () -> Unit,
    footer: @Composable (() -> Unit)? = null
) {
    val scrollState = rememberScrollState()

    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    val horizontalPadding = if (isPortrait) 0.dp else 48.dp

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            header?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .statusBarsPadding(),
                    contentAlignment = Alignment.Center
                ) {
                    it()
                }
            }
        },
        bottomBar = {
            footer?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .navigationBarsPadding(),
                    contentAlignment = Alignment.Center
                ) {
                    it()
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = if (header != null) innerPadding.calculateTopPadding() else 0.dp,
                    bottom = if (footer != null) innerPadding.calculateBottomPadding() else 0.dp,
                    start = horizontalPadding,
                    end = horizontalPadding
                )
                .then(if (isBodyScrollable) Modifier.verticalScroll(scrollState) else Modifier)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            body()
        }
    }
}