package com.devpaul.infoxperu.feature.home.services.ui

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.BaseViewModel
import com.devpaul.infoxperu.domain.models.res.Contact
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    dataStoreUseCase: DataStoreUseCase
) : BaseViewModel<ContactUiEvent>(dataStoreUseCase) {

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
                }
                _contactState.value = ResultState.Success(contactsList)
            }
            .addOnFailureListener { exception ->
                _contactState.value = ResultState.Error(exception)
                Timber.e(exception)
            }
    }
}