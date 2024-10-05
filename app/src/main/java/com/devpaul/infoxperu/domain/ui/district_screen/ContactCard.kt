package com.devpaul.infoxperu.domain.ui.district_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.feature.user_start.Screen
import com.devpaul.infoxperu.ui.theme.BackgroundBlack
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun ContactCard(
    navController: NavController,
    contact: Contact,
) {
    ContactCardContent(
        navController = navController,
        contact = contact,
    )
}

@Composable
fun ContactCardContent(
    navController: NavController,
    contact: Contact,
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .height(140.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
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
                painter = rememberAsyncImagePainter(contact.imageUrl),
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
                text = contact.title ?: "",
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
fun ContactCardSuccessPreview() {
    ContactCardContent(
        navController = rememberNavController(),
        contact = Contact(
            title = "Serenazgo",
            type = "serenazgo",
            imageUrl = "https://www.deperu.com"
        ),
    )
}

