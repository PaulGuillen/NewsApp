package com.devpaul.infoxperu.domain.ui.service_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.mocks.ServiceMock
import com.devpaul.infoxperu.domain.models.res.Service
import com.devpaul.infoxperu.domain.ui.news_screen.errors.ErrorCard
import com.devpaul.infoxperu.domain.ui.skeleton.ContactSkeleton

@Composable
fun ServicesCards(
    navController: NavHostController,
    serviceState: ResultState<List<Service>>,
) {
    when (serviceState) {
        is ResultState.Loading -> {
            ContactSkeleton()
        }

        is ResultState.Success -> {
            if (serviceState.data.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(serviceState.data) { service ->
                        ServiceCard(navController = navController, serviceState = service)
                    }
                }
            } else {
                ErrorCard()
            }
        }

        is ResultState.Error -> {
            ErrorCard()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServicesCardSuccessPreview() {
    val mock = ServiceMock()
    val serviceState = ResultState.Success(mock.serviceListMock)
    ServicesCards(
        navController = rememberNavController(),
        serviceState = serviceState,
    )
}

@Preview(showBackground = true)
@Composable
fun ServicesCardLoadingPreview() {
    ServicesCards(
        navController = rememberNavController(),
        serviceState = ResultState.Loading,
    )
}

@Preview(showBackground = true)
@Composable
fun ServicesCardErrorPreview() {
    ServicesCards(
        navController = rememberNavController(),
        serviceState = ResultState.Error(Exception("Failed to load data")),
    )
}
