package com.devpaul.infoxperu.feature.home.contacts_view.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.domain.screen.atomic.DividerView
import com.devpaul.infoxperu.domain.ui.contacts_screen.ContactsCard
import com.devpaul.infoxperu.domain.ui.contacts_screen.ServiceCardInformation
import com.devpaul.infoxperu.domain.ui.utils.BottomNavigationBar
import com.devpaul.infoxperu.domain.ui.utils.TopBar

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
            innerPadding = innerPadding,
            context = context,
            contactState = contactState,
        )
    }
}

@Composable
fun ContentScreenContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(),
    context: Context,
    contactState: ResultState<List<Contact>>
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        ServiceCardInformation()
        Spacer(modifier = Modifier.padding(10.dp))
        DividerView()
        Spacer(modifier = Modifier.padding(10.dp))
        ContactsCard(contactState = contactState, context = context)
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

