package com.devpaul.infoxperu.feature.home.services_view.ui.all_services

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Service
import timber.log.Timber

@Composable
fun DistrictManagement(
    navController: NavController,
    districtSelected: String? // ancón, ate, etc.
) {
    val viewModel: AllServicesViewModel = hiltViewModel()
    val serviceData by viewModel.serviceSelected.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                viewModel.fetchAllServicesForDistrict(districtSelected)
            }
        ) {
            Text("Ver Todo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (serviceData) {
            is ResultState.Loading -> {
                Timber.d("Services : Cargando datos...")
            }

            is ResultState.Success -> {
                val services = (serviceData as ResultState.Success<List<Service>>).data
                Timber.d("Services: $services")
                services.forEach { service ->
                    Toast.makeText(LocalContext.current, service.title, Toast.LENGTH_SHORT).show()
                }
            }

            is ResultState.Error -> {
                Text(text = "Error al cargar los datos.")
                Timber.e("Services : Error al cargar los datos.")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAllServices() {
    DistrictManagement(
        navController = NavController(LocalContext.current),
        districtSelected = "policía"
    )
}