package com.devpaul.infoxperu.feature.util.ui.service_screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.core.extension.EMPTY
import com.devpaul.infoxperu.domain.models.res.District
import com.devpaul.infoxperu.feature.Screen
import com.devpaul.infoxperu.ui.theme.Black
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun DistrictCard(
    context: Context,
    navController: NavController,
    district: District
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(160.dp)
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = White,
            contentColor = Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {
            navController.navigate(
                Screen.DistrictManagement.createRoute(
                    districtSelected = district.type ?: String.EMPTY
                )
            )
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = com.devpaul.infoxperu.R.drawable.baseline_location_city_24),
                contentDescription = String.EMPTY,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = district.title ?: String.EMPTY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DistrictCardPreview() {
    DistrictCard(
        context = LocalContext.current,
        navController = rememberNavController(),
        district = District(
            title = "Ancon",
            type = "ancon"
        )
    )
}
