package com.devpaul.infoxperu.domain.ui.district_screen

import android.content.Context
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.ui.news_screen.CountryCard
import com.devpaul.infoxperu.domain.ui.skeleton.CountryCardSkeleton
import com.devpaul.infoxperu.feature.user_start.Screen

@Composable
fun DistrictManagement(
    countryState: ResultState<List<Contact>>,
    context: Context,
    onContactSelected: (Contact) -> Unit
) {
    DistrictManagementContent(
        countryState = countryState,
        context = context,
        onContactSelected = onContactSelected
    )
}

@Composable
fun DistrictManagementContent(
    countryState: ResultState<List<Contact>>,
    context: Context,
    onContactSelected: (Contact) -> Unit
) {

    var selectedCountry by remember { mutableStateOf<Contact?>(null) }

    when (countryState) {
        is ResultState.Loading -> {
            CountryCardSkeleton()
        }

        is ResultState.Success -> {
            if (countryState.data.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 10.dp)
                ) {
                    countryState.data.forEach { countryItem ->
                        ContactCard(navController = rememberNavController(), contact = countryItem)
                    }
                }
            } else {
                Text(text = "No hay secciones disponibles.")
            }
        }

        is ResultState.Error -> {
            // Manejo del error
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DistrictManagementContentPreview() {
    val countryState = ResultState.Success(
        listOf(
            Contact(
                title = "Polica Nacional del Perú",
                type = "policia",
                imageUrl = "https://www.deperu.com"
            ),
            Contact(
                title = "Bomberos",
                type = "bombero",
                imageUrl = "https://www.deperu.com"
            )
        )
    )
    DistrictManagementContent(
        countryState = countryState,
        context = LocalContext.current,
        onContactSelected = {})
}

@Preview(showBackground = true)
@Composable
fun DistrictManagementSkeleton() {
    CountryCardSkeleton()
}
