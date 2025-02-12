package com.devpaul.infoxperu.feature.home.services.ui.district_screen

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.BaseViewModel
import com.devpaul.infoxperu.domain.models.res.District
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.devpaul.infoxperu.feature.home.services.ui.ContactUiEvent
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DistrictViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    dataStoreUseCase: DataStoreUseCase
) : BaseViewModel<ContactUiEvent>(dataStoreUseCase) {

    private val _districtState =
        MutableStateFlow<ResultState<List<District>>>(ResultState.Loading)
    val districtState: StateFlow<ResultState<List<District>>> = _districtState

    init {
        fetchDistricts()
    }

    private fun fetchDistricts() {
        _districtState.value = ResultState.Loading

        firestore.collection("districts")
            .get()
            .addOnSuccessListener { documents ->
                val districtList = documents.map { document ->
                    document.toObject(District::class.java)
                }.sortedBy { it.title?.lowercase() }

                _districtState.value = ResultState.Success(districtList)
            }
            .addOnFailureListener { exception ->
                _districtState.value = ResultState.Error(exception)
                Timber.e(exception)
            }
    }

}