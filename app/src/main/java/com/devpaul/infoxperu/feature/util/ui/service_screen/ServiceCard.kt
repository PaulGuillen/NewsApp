package com.devpaul.infoxperu.feature.util.ui.service_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.devpaul.infoxperu.core.extension.EMPTY
import com.devpaul.infoxperu.core.mocks.ServiceMock
import com.devpaul.infoxperu.domain.models.res.Service
import com.devpaul.infoxperu.feature.Screen
import com.devpaul.infoxperu.ui.theme.BackgroundBlack
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun ServiceCard(navController: NavHostController, serviceState: Service) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        onClick = {
            navController.navigate(Screen.Districts.route)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(serviceState.imageOne),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundBlack.copy(alpha = 0.5f))
            )
            Text(
                text = serviceState.title ?: String.EMPTY,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = White,
                fontSize = 18.sp,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServiceCardPreview() {
    val serviceMock = ServiceMock()
    val service = serviceMock.singleServiceMock
    ServiceCard(navController = rememberNavController(), serviceState = service)
}