package com.devpaul.infoxperu.feature.home.services_view.ui.management

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.core.extension.EMPTY
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.mocks.ContactMock
import com.devpaul.infoxperu.core.mocks.ServiceMock
import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.domain.models.res.Service
import com.devpaul.infoxperu.domain.screen.atomic.DividerView
import com.devpaul.infoxperu.domain.ui.service_screen.SectionsRowContact
import com.devpaul.infoxperu.domain.ui.service_screen.ServicesCards
import com.devpaul.infoxperu.domain.ui.utils.TopBar

@Composable
fun DistrictManagement(
    navController: NavHostController,
    districtSelected: String?,
    viewModel: DistrictManagementViewModel = hiltViewModel(),
) {

    val contactState by viewModel.contactState.collectAsState()
    val serviceState by viewModel.serviceState.collectAsState()
    val context = LocalContext.current
    var selectedContact by remember { mutableStateOf<String?>(String.EMPTY) }

    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.app_name),
                onBackClick = {
                    navController.popBackStack()
                })
        }
    ) { innerPadding ->
        DistrictContentManagement(
            modifier = Modifier.fillMaxSize(),
            contactState = contactState,
            serviceState = serviceState,
            innerPadding = innerPadding,
            navController = navController,
            selectedContact = selectedContact,
            onCountrySelected = { contact ->
                selectedContact = contact
                viewModel.fetchServicePerDistrictSelected(
                    districtSelected = districtSelected.toString(),
                    serviceSelected = contact
                )
            },
            context = context
        )
    }
}

@Composable
fun DistrictContentManagement(
    modifier: Modifier,
    contactState: ResultState<List<Contact>>,
    serviceState: ResultState<List<Service>>,
    innerPadding: PaddingValues = PaddingValues(),
    navController: NavHostController,
    selectedContact: String?,
    onCountrySelected: (String) -> Unit,
    context: Context
) {
    Column(
        modifier = modifier
            .padding(innerPadding)
    ) {
        SectionsRowContact(
            navHostController = navController,
            sectionContactState = contactState,
            context = context,
            onContactSelected = { contactType ->
                onCountrySelected(contactType)
            }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        DividerView()
        Spacer(modifier = Modifier.padding(10.dp))
        if (selectedContact.isNullOrEmpty()) {
            //Do something
        } else {
            ServicesCards(
                navController = navController,
                serviceState = serviceState,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessDistrictSelected() {

    val contactMock = ContactMock()
    val serviceMock = ServiceMock()
    val mockContactState = ResultState.Success(contactMock.contactListMock)
    val serviceMockState = ResultState.Success(serviceMock.serviceListMock)

    DistrictContentManagement(
        modifier = Modifier.fillMaxSize(),
        serviceState = serviceMockState,
        contactState = mockContactState,
        navController = rememberNavController(),
        selectedContact = "Policía",
        onCountrySelected = { },
        context = LocalContext.current
    )
}
