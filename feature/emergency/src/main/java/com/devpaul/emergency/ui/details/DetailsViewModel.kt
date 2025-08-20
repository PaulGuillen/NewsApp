package com.devpaul.emergency.ui.details

import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.emergency.domain.usecase.CivilDefenseUC
import com.devpaul.emergency.domain.usecase.GeneralUC
import com.devpaul.emergency.domain.usecase.LimaSecurityUC
import com.devpaul.emergency.domain.usecase.ProvinceSecurityUC
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class DetailsViewModel(
    private val generalUC: GeneralUC,
    private val civilDefenseUC: CivilDefenseUC,
    private val limaSecurityUC: LimaSecurityUC,
    private val provinceSecurityUC: ProvinceSecurityUC,
) : StatefulViewModel<DetailsUiState, DetailsUiIntent, DetailsUiEvent>(
    defaultUIState = {
        DetailsUiState()
    }
) {

    private var typeService: String = ""

    override suspend fun onUiIntent(intent: DetailsUiIntent) {

    }

    private fun fetchGeneral() {
        Timber.d("Fetching general service details")
        // Implement fetching logic for general service
    }

    private fun fetchCivilDefense() {
        Timber.d("Fetching civil defense service details")
        // Implement fetching logic for civil defense service
    }

    private fun fetchPolice() {
        Timber.d("Fetching police service details")
        // Implement fetching logic for police service
    }

    private fun fetchFirefighter() {
        Timber.d("Fetching firefighter service details")
        // Implement fetching logic for firefighter service
    }

    private fun fetchLocalSecurity() {
        Timber.d("Fetching local security service details")
        // Implement fetching logic for local security service
    }


    fun getTypeService(type: String?) {
        Timber.d("getTypeService: $type")
        typeService = type ?: ""
        when (typeService) {
            "general" -> fetchGeneral()
            else -> Timber.w("Unknown service type: $typeService")
        }
    }
}