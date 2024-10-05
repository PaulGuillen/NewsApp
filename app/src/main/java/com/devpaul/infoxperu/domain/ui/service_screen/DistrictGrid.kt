package com.devpaul.infoxperu.domain.ui.service_screen

import DistrictGridSkeleton
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.extension.removeAccents
import com.devpaul.infoxperu.domain.models.res.District
import com.devpaul.infoxperu.domain.ui.utils.DistrictCard
import timber.log.Timber

@Composable
fun DistrictGrid(
    navController: NavController,
    context: Context,
    districtState: ResultState<List<District>>,
    searchQuery: String
) {
    var showSkeleton by remember { mutableStateOf(true) }

    LaunchedEffect(districtState) {
        showSkeleton = districtState is ResultState.Loading
    }

    if (showSkeleton) {
        DistrictGridSkeleton()
    } else {
        DistrictCardContent(
            navController = navController,
            districtState = districtState,
            searchQuery = searchQuery,
            context = context,
        )
    }
}

@Composable
fun DistrictCardContent(
    navController: NavController,
    districtState: ResultState<List<District>>,
    searchQuery: String,
    context: Context,
) {
    when (districtState) {
        is ResultState.Loading -> {
            DistrictGridSkeleton()
        }

        is ResultState.Success -> {
            val normalizedSearchQuery = removeAccents(searchQuery)
            val filteredDistricts = districtState.data.filter {
                val normalizedTitle = removeAccents(it.title ?: "")
                normalizedTitle.contains(normalizedSearchQuery, ignoreCase = true)
            }
            Timber.d("Filtered districts: $filteredDistricts")

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(filteredDistricts) { district ->
                    DistrictCard(context, navController, district)
                }
            }
        }

        is ResultState.Error -> {
            // Error message
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DistrictGridPreview() {
    val navController = rememberNavController()
    DistrictGrid(
        navController = navController,
        context = LocalContext.current,
        districtState = ResultState.Success(
            listOf(
                District("Ancon", "ancon"),
                District("Carabayllo", "carabayllo"),
                District("San Borja", "san_borja"),
                District("San Isidro", "san_isidro"),
                District("Miraflores", "miraflores"),
                District("Surco", "surco")
            )
        ),
        searchQuery = ""
    )
}

@Preview(showBackground = true)
@Composable
fun DistrictGridLoadingPreview() {
    val navController = rememberNavController()
    DistrictGrid(
        navController = navController,
        context = LocalContext.current,
        districtState = ResultState.Loading,
        searchQuery = ""
    )
}
