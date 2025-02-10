package com.devpaul.infoxperu.domain.ui.service_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.domain.ui.skeleton.ContactSkeleton
import com.devpaul.infoxperu.feature.Screen
import com.devpaul.infoxperu.ui.theme.BackgroundBlack
import com.devpaul.infoxperu.ui.theme.Black
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun ContactsCard(
    navController: NavController,
    contactState: ResultState<List<Contact>>,
) {
    ContactsCardContent(
        navController = navController,
        contactState = contactState,
    )
}

@Composable
fun ContactsCardContent(
    navController: NavController,
    contactState: ResultState<List<Contact>>,
) {
    when (contactState) {
        is ResultState.Loading -> {
            ContactSkeleton()
        }

        is ResultState.Success -> {
            if (contactState.data.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(contactState.data) { contact ->
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
                }
            } else {
                ContactSkeleton()
            }
        }

        is ResultState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                repeat(1) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(8.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = White,
                            contentColor = Black
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(BackgroundBlack.copy(alpha = 0.25f))
                            )
                            Text(
                                text = "Error al cargar los contactos",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = White,
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactSectionSuccessPreview() {
    val contactState = ResultState.Success(
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
    ContactsCardContent(
        navController = rememberNavController(),
        contactState = contactState,
    )
}

@Preview(showBackground = true)
@Composable
fun ContactSectionLoadingPreview() {
    val contactState = ResultState.Loading
    ContactsCardContent(
        navController = rememberNavController(),
        contactState = contactState,
    )
}

@Preview(showBackground = true)
@Composable
fun ContactSectionErrorPreview() {
    val contactState = ResultState.Error(Exception("Failed to load data"))
    ContactsCardContent(
        navController = rememberNavController(),
        contactState = contactState,
    )
}
