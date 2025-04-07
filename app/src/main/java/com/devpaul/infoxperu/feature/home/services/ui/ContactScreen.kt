package com.devpaul.infoxperu.feature.home.services.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.feature.util.ui.service_screen.ContactsCard
import com.devpaul.infoxperu.feature.util.ui.utils.BottomNavigationBar
import com.devpaul.infoxperu.feature.util.ui.utils.TopBar

@Composable
fun ContactScreen(
    viewModel: ContactViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val contactState by viewModel.contactState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.app_name),
                onLogoutClick = {
                    viewModel.logOut(navController)
                })
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        ContentScreenContent(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            innerPadding = innerPadding,
            context = context,
            contactState = contactState,
        )
    }
}

@Composable
fun ContentScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues = PaddingValues(),
    context: Context,
    contactState: ResultState<List<Contact>>
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
    ) {
        ContactsCard(navController = navController, contactState = contactState)
    }
}

@Preview(showBackground = true)
@Composable
fun ServiceScreenPreview() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopBar(title = "InfoPerú")
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        ContentScreenContent(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            innerPadding = innerPadding,
            context = LocalContext.current,
            contactState = ResultState.Success(
                listOf(
                    Contact(
                        title = "Policia Nacional del Perú",
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
        )
    }
}

