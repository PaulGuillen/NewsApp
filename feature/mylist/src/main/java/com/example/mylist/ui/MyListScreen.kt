package com.example.mylist.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.devpaul.shared.data.datasource.NewsSavedStore
import com.devpaul.shared.data.datasource.displayTime
import com.devpaul.shared.ui.components.molecules.AppHeader
import com.devpaul.shared.ui.components.molecules.HomeBottomBar
import com.devpaul.shared.ui.components.organisms.BaseContentLayout
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import com.example.mylist.ui.components.NewsSavedCard
import com.example.mylist.ui.components.TabItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyListScreen(navHostController: NavHostController) {
    val viewModel: MyListViewModel = koinViewModel()

    BaseScreenWithState(viewModel = viewModel, navController = navHostController) { _, _, _, _, _ ->
        BaseContentLayout(
            isBodyScrollable = true,
            header = { MyListHeader() },
            body = { MyListContent() },
            footer = { HomeBottomBar(navHostController) }
        )
    }
}

@Composable
fun MyListHeader() {
    AppHeader(title = "Emergencias PE", subtitle = "Lunes, 24 de Mayo", icon = Icons.Default.Public, onNotificationClick = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyListContent() {
    val isDark = isSystemInDarkTheme()
    val background = if (isDark) Color(0xFF000000) else Color(0xFFFCFCFC)
    val divider = if (isDark) Color(0xFF1E293B) else Color(0xFFE5E7EB)

    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedArticleId by remember { mutableStateOf<String?>(null) }
    val ctx = LocalContext.current
    val savedFlow = remember { NewsSavedStore.getSavedNewsFlow(ctx) }
    val savedList by savedFlow.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().background(background).padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            TabItem("Por leer", selectedTab == 0) {
                selectedTab = 0
                selectedArticleId = null
            }
            Spacer(modifier = Modifier.height(0.dp))
            TabItem("Leídas", selectedTab == 1) {
                selectedTab = 1
                selectedArticleId = null
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(Modifier, DividerDefaults.Thickness, color = divider)
        Spacer(modifier = Modifier.height(16.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (savedList.isEmpty()) {
                NewsSavedCard(title = "No tienes artículos guardados", category = "", time = "", isHighlighted = false)
            } else {
                val filtered = savedList.filter { if (selectedTab == 0) !it.isRead else it.isRead }
                if (filtered.isEmpty()) {
                    NewsSavedCard(title = if (selectedTab == 0) "No hay artículos por leer" else "No hay artículos leídos", category = "", time = "", isHighlighted = false)
                } else {
                    filtered.forEach { item ->
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { value ->
                                if (value == SwipeToDismissBoxValue.EndToStart) {
                                    if (selectedArticleId == item.id) {
                                        selectedArticleId = null
                                    }
                                    scope.launch { NewsSavedStore.removeArticle(ctx, item.id) }
                                }
                                true
                            }
                        )

                        SwipeToDismissBox(
                            state = dismissState,
                            enableDismissFromStartToEnd = false,
                            backgroundContent = {}
                        ) {
                            NewsSavedCard(
                                title = item.title,
                                category = item.country ?: "",
                                badgeLabel = item.source ?: item.category ?: "",
                                badgeSource = item.source ?: "",
                                time = item.time ?: item.displayTime(),
                                isHighlighted = selectedArticleId == item.id,
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    selectedArticleId = item.id
                                    val link = item.url
                                    if (!link.isNullOrBlank()) {
                                        try { ctx.startActivity(Intent(Intent.ACTION_VIEW, link.toUri())) } catch (_: Throwable) {}
                                    }
                                    scope.launch {
                                        delay(600)
                                        NewsSavedStore.toggleRead(ctx, item.id, true)
                                        selectedTab = 1
                                        selectedArticleId = null
                                    }
                                },
                                onDelete = {
                                    if (selectedArticleId == item.id) {
                                        selectedArticleId = null
                                    }
                                    scope.launch { NewsSavedStore.removeArticle(ctx, item.id) }
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
