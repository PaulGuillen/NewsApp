package com.devpaul.infoxperu.domain.ui.contacts_screen.district_screen

import DistrictCard
import DistrictGridSkeleton
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.District
import com.devpaul.infoxperu.ui.theme.BrickRed

@Composable
fun DistrictGrid(
    navController: NavController,
    context: Context,
    serviceSelected: String?,
    districtState: ResultState<List<District>>
) {

    var showSkeleton by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(districtState) {
        showSkeleton = districtState is ResultState.Loading
    }

    if (showSkeleton) {
        DistrictGridSkeleton()
    } else {
        Column(modifier = Modifier.fillMaxSize()) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar distrito") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = stringResource(id = R.string.icon_search)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrickRed,
                    cursorColor = BrickRed,
                    focusedLabelColor = BrickRed
                )
            )

            DistrictCardContent(
                navController = navController,
                districtState = districtState,
                searchQuery = searchQuery,
                context = context,
                serviceSelected = serviceSelected
            )
        }
    }
}

@Composable
fun DistrictCardContent(
    navController: NavController,
    districtState: ResultState<List<District>>,
    searchQuery: String,
    context: Context,
    serviceSelected: String?
) {
    when (districtState) {
        is ResultState.Loading -> {
            DistrictGridSkeleton()
        }

        is ResultState.Success -> {
            val filteredDistricts = districtState.data.filter {
                it.title?.contains(searchQuery, ignoreCase = true) ?: false
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(filteredDistricts) { district ->
                    DistrictCard(context, serviceSelected, navController, district)
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
        context = androidx.compose.ui.platform.LocalContext.current,
        serviceSelected = "Lima",
        districtState = ResultState.Success(
            listOf(
                District("Ancon", "ancon"),
                District("Carabayllo", "carabayllo"),
                District("San Borja", "san_borja"),
                District("San Isidro", "san_isidro"),
                District("Miraflores", "miraflores"),
                District("Surco", "surco")
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DistrictGridLoadingPreview() {
    val navController = rememberNavController()
    DistrictGrid(
        navController = navController,
        context = androidx.compose.ui.platform.LocalContext.current,
        serviceSelected = "Lima",
        districtState = ResultState.Loading
    )
}
