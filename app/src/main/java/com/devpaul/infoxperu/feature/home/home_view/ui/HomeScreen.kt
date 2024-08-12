package com.devpaul.infoxperu.feature.home.home_view.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.domain.ui.BottomNavigationBar
import com.devpaul.infoxperu.domain.ui.InformationCard
import com.devpaul.infoxperu.domain.ui.SectionHeader
import com.devpaul.infoxperu.domain.ui.UITCard
import com.devpaul.infoxperu.ui.theme.InfoXPeruTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "LimaSegura", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* Handle settings click */ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            SectionHeader("Secciones disponibles")
            SectionsRow()
            SectionHeader("Información diaria")
            InformationCard(
                imageRes = R.drawable.background_dolar_home,
                title = "Valor del dólar (USD)",
                buys = "Compra",
                sale = "Venta",
                place = "Sitio",
                date = "Fecha"
            )
            UITCard(
                imageRes = R.drawable.ic_launcher_background,
                title = "Valor de UIT",
                uit = 5150.0,
                periodo = 2024,
                fuente = "DePeru.com"
            )
        }
    }
}

@Composable
fun SectionsRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SectionItem(iconRes = R.drawable.ic_launcher_background, title = "Noticias")
        SectionItem(iconRes = R.drawable.ic_launcher_background, title = "Servicios")
        SectionItem(iconRes = R.drawable.ic_launcher_background, title = "Distritos")
    }
}

@Composable
fun SectionItem(iconRes: Int, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            modifier = Modifier.size(64.dp)
        )
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    InfoXPeruTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController)
    }
}
