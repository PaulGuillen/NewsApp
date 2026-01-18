package com.devpaul.news.ui.news.components.news

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import com.devpaul.news.ui.news.NewsViewModel.Companion.PAGE_SIZE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import timber.log.Timber

@Composable
fun rememberUiPagination(
    totalItems: Int
): Triple<Int, LazyListState, Boolean> {

    Timber.tag("UiPagination").d("INIT → totalItems=$totalItems")

    var visibleCount by remember(totalItems) {
        Timber.tag("UiPagination").d("INIT visibleCount=$PAGE_SIZE")
        mutableIntStateOf(PAGE_SIZE)
    }

    var isLoadingMore by remember(totalItems) {
        Timber.tag("UiPagination").d("INIT isLoadingMore=false")
        mutableStateOf(false)
    }

    val listState = rememberLazyListState()
    val hasMoreItems = visibleCount < totalItems

    LaunchedEffect(listState, visibleCount, totalItems) {
        snapshotFlow {
            val lastVisibleIndex =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1

            Timber.tag("UiPagination").d(
                "SCROLL → lastVisibleIndex=$lastVisibleIndex, visibleCount=$visibleCount, totalItems=$totalItems"
            )

            lastVisibleIndex
        }
            .distinctUntilChanged()
            .filter { lastIndex ->
                val shouldTrigger =
                    hasMoreItems &&
                            !isLoadingMore &&
                            lastIndex >= visibleCount - 3

                Timber.tag("UiPagination").d(
                    "CHECK → lastIndex=$lastIndex, shouldTrigger=$shouldTrigger"
                )

                shouldTrigger
            }
            .collectLatest {
                Timber.tag("UiPagination").i("LOAD MORE → start")

                isLoadingMore = true
                delay(2000)

                val newCount =
                    (visibleCount + PAGE_SIZE).coerceAtMost(totalItems)

                Timber.tag("UiPagination").i(
                    "LOAD MORE → visibleCount: $visibleCount → $newCount"
                )

                visibleCount = newCount
                isLoadingMore = false

                Timber.tag("UiPagination").i("LOAD MORE → end")
            }
    }

    Timber.tag("UiPagination").d(
        "RETURN → visibleCount=$visibleCount, showFooter=${isLoadingMore && hasMoreItems}"
    )

    return Triple(
        visibleCount,
        listState,
        isLoadingMore && hasMoreItems
    )
}