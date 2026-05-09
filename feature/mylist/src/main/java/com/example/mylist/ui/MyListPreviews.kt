package com.example.mylist.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.shared.ui.components.molecules.HomeBottomBar
import com.devpaul.shared.ui.components.organisms.BaseContentLayout

@Preview(
    name = "MyList - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun MyListPreviewLight() {
    InfoXPeruTheme(darkTheme = false, dynamicColor = false) {
        BaseContentLayout(
            isBodyScrollable = true,
            header = { MyListHeader() },
            body = { MyListContent() },
            footer = { HomeBottomBar(rememberNavController()) }
        )
    }
}

@Preview(
    name = "MyList - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MyListPreviewDark() {
    InfoXPeruTheme(darkTheme = true, dynamicColor = false) {
        BaseContentLayout(
            isBodyScrollable = true,
            header = { MyListHeader() },
            body = { MyListContent() },
            footer = { HomeBottomBar(rememberNavController()) }
        )
    }
}