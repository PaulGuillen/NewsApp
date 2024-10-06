package com.devpaul.infoxperu.feature.home.services_view.ui.management

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.BaseViewModel
import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.domain.models.res.Service
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.devpaul.infoxperu.feature.home.services_view.ui.ContactUiEvent
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DistrictManagementViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    dataStoreUseCase: DataStoreUseCase
) : BaseViewModel<ContactUiEvent>(dataStoreUseCase) {

    private val _serviceState =
        MutableStateFlow<ResultState<List<Service>>>(ResultState.Loading)
    val serviceState: StateFlow<ResultState<List<Service>>> = _serviceState

    private val _contactState =
        MutableStateFlow<ResultState<List<Contact>>>(ResultState.Loading)
    val contactState: StateFlow<ResultState<List<Contact>>> = _contactState

    init {
        fetchContacts()
    }

    private fun fetchContacts() {
        _contactState.value = ResultState.Loading

        firestore.collection("contacts")
            .get()
            .addOnSuccessListener { documents ->
                val contactsList = documents.map { document ->
                    document.toObject(Contact::class.java)
                }.sortedByDescending { contact ->
                    contact.type == "policia"
                }
                _contactState.value = ResultState.Success(contactsList)
            }
            .addOnFailureListener { exception ->
                _contactState.value = ResultState.Error(exception)
                Timber.e(exception)
            }
    }

    fun fetchServicePerDistrictSelected(districtSelected: String, serviceSelected: String) {
        firestore.collection("districts")
            .whereEqualTo("type", districtSelected)
            .get()
            .addOnSuccessListener { documents ->
                val allServices = mutableListOf<Service>()
                for (document in documents) {
                    val districtId = document.id
                    firestore.collection("districts").document(districtId)
                        .collection(serviceSelected)
                        .get()
                        .addOnSuccessListener { fetchServices ->
                            for (services in fetchServices) {
                                val service = services.toObject(Service::class.java)
                                service.let { allServices.add(it) }
                            }
                            _serviceState.value = ResultState.Success(allServices)
                            Timber.d("AllServices - Document data: $allServices")
                        }
                        .addOnFailureListener { exception ->
                            Timber.e(exception, "Error fetching bombero services")
                            _serviceState.value =
                                ResultState.Error(Exception("Error fetching bombero services"))
                        }
                }
            }
            .addOnFailureListener { exception ->
                Timber.e(exception, "Error fetching services")
                _serviceState.value = ResultState.Error(Exception("Error fetching services"))
            }

    }
}
