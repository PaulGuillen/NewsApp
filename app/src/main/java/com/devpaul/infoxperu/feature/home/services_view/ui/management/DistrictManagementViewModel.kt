package com.devpaul.infoxperu.feature.home.services_view.ui.management

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.BaseViewModel
import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.domain.models.res.Service
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.devpaul.infoxperu.feature.home.services_view.ui.ContactUiEvent
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
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

    private val _serviceSelected =
        MutableStateFlow<ResultState<List<Service>>>(ResultState.Loading)
    val serviceSelected: StateFlow<ResultState<List<Service>>> = _serviceSelected

    private val _contactState =
        MutableStateFlow<ResultState<List<Contact>>>(ResultState.Loading)
    val contactState: StateFlow<ResultState<List<Contact>>> = _contactState

    private val gson: Gson = Gson()

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
                }
                _contactState.value = ResultState.Success(contactsList)
            }
            .addOnFailureListener { exception ->
                _contactState.value = ResultState.Error(exception)
                Timber.e(exception)
            }
    }

    fun fetchAllServicesForDistrict(districtType: String?) {
        if (districtType == null) return

        firestore.collection("districts")
            .whereEqualTo("type", districtType)  // Filtramos por el campo 'type'
            .get()
            .addOnSuccessListener { querySnapshot ->
                val allServices = mutableListOf<Service>()

                for (document in querySnapshot) {
                    Timber.d("AllServices - Document data: ${document.data}")

                    // Ahora accedemos a las colecciones dentro del documento, como 'bombero', 'policia' y 'serenazgo'
                    val districtId = document.id

                    // Cargar colección 'bombero'
                    firestore.collection("districts").document(districtId).collection("bombero")
                        .get()
                        .addOnSuccessListener { bomberoDocuments ->
                            for (bomberoDocument in bomberoDocuments) {
                                val bombero = bomberoDocument.toObject(Service::class.java)
                                bombero.let { allServices.add(it) }
                            }

                            // Cargar colección 'policia'
                            firestore.collection("districts").document(districtId)
                                .collection("policia")
                                .get()
                                .addOnSuccessListener { policiaDocuments ->
                                    for (policiaDocument in policiaDocuments) {
                                        val policia = policiaDocument.toObject(Service::class.java)
                                        policia.let { allServices.add(it) }
                                    }

                                    // Cargar colección 'serenazgo'
                                    firestore.collection("districts").document(districtId)
                                        .collection("serenazgo")
                                        .get()
                                        .addOnSuccessListener { serenazgoDocuments ->
                                            for (serenazgoDocument in serenazgoDocuments) {
                                                val serenazgo =
                                                    serenazgoDocument.toObject(Service::class.java)
                                                serenazgo.let { allServices.add(it) }
                                            }

                                            // Finalmente, actualizamos el estado con todos los servicios obtenidos
                                            _serviceSelected.value =
                                                ResultState.Success(allServices)
                                        }
                                        .addOnFailureListener { exception ->
                                            Timber.e(exception, "Error fetching serenazgo services")
                                            _serviceSelected.value =
                                                ResultState.Error(Exception("Error fetching serenazgo services"))
                                        }
                                }
                                .addOnFailureListener { exception ->
                                    Timber.e(exception, "Error fetching policia services")
                                    _serviceSelected.value =
                                        ResultState.Error(Exception("Error fetching policia services"))
                                }
                        }
                        .addOnFailureListener { exception ->
                            Timber.e(exception, "Error fetching bombero services")
                            _serviceSelected.value =
                                ResultState.Error(Exception("Error fetching bombero services"))
                        }
                }
            }
            .addOnFailureListener { exception ->
                Timber.e(exception, "Error fetching districts")
                _serviceSelected.value = ResultState.Error(Exception("Error fetching districts"))
            }
    }

    fun fetchService(serviceSelected: String?, districtType: String?) {
        if (serviceSelected == null || districtType == null) return

        firestore.collection("districts")
            .document(districtType)
            .collection(serviceSelected)
            .get()
            .addOnSuccessListener { documents ->
                val services = documents.map {
                    val json = gson.toJson(it.data)  // Convertir el documento Firestore a JSON
                    gson.fromJson(
                        json,
                        Service::class.java
                    )  // Convertir el JSON a un objeto Service
                }
                _serviceSelected.value = ResultState.Success(services)
            }
            .addOnFailureListener { exception ->
                Timber.e(exception, "Error fetching services")
                _serviceSelected.value = ResultState.Error(Exception("Error fetching services"))
            }
    }
}
