package com.devpaul.infoxperu.feature.home.services_view.ui.management

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.mocks.ContactMock
import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.domain.ui.service_screen.SectionsRowContact
import com.devpaul.infoxperu.domain.ui.utils.TopBar

@Composable
fun DistrictManagement(
    navController: NavHostController,
    districtSelected: String?
) {
    val viewModel: DistrictManagementViewModel = hiltViewModel()
    val contactState by viewModel.contactState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.app_name),
                onLogoutClick = {
                    viewModel.logOut(navController)
                })
        }
    ) { innerPadding ->
        DistrictContentManagement(
            modifier = Modifier.fillMaxSize(),
            contactState = contactState,
            innerPadding = innerPadding,
            navController = navController,
            districtSelected = districtSelected,
            context = context
        )
    }
}

@Composable
fun DistrictContentManagement(
    modifier: Modifier,
    contactState: ResultState<List<Contact>>,
    innerPadding: PaddingValues = PaddingValues(),
    navController: NavHostController,
    districtSelected: String?,
    context: Context
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        SectionsRowContact(
            navHostController = navController,
            sectionContactState = contactState,
            context = context,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDistrictContentManagement() {

    val contactMock = ContactMock()
    val mockContactState = ResultState.Success(contactMock.contactListMock)

    DistrictContentManagement(
        modifier = Modifier.fillMaxSize(),
        contactState = mockContactState,
        navController = rememberNavController(),
        districtSelected = "Distrito 1",
        context = LocalContext.current
    )
}
