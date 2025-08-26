package com.devpaul.emergency.ui.details

import com.devpaul.core_platform.extension.ResultState
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
        when (intent) {
            is DetailsUiIntent.CallNumber -> {
                val clean = intent.phone.filter { it.isDigit() || it == '+' }
                DetailsUiEvent.RequireCallPermission(clean).send()
            }

            is DetailsUiIntent.CallGeneralService -> {
                launchIO { fetchGeneral() }
            }
        }
    }

    private suspend fun fetchGeneral() {
        updateUiStateOnMain { it.copy(generalCase = true) }
        updateUiStateOnMain { it.copy(general = ResultState.Loading) }
        val result = generalUC(GeneralUC.Params(page = 1, perPage = 10))
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is GeneralUC.Success.GeneralSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(general = ResultState.Success(it.general))
                        }
                    }
                }
            }
            .onFailure<GeneralUC.Failure> {
                when (it) {
                    is GeneralUC.Failure.GeneralError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                general = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "Error al cargar los servicios generales"
                                ),
                            )
                        }
                    }
                }
            }
    }

    private suspend fun fetchCivilDefense() {
        updateUiStateOnMain { it.copy(civilCase = true) }
        updateUiStateOnMain { it.copy(civilDefense = ResultState.Loading) }
        val result = civilDefenseUC(CivilDefenseUC.Params(page = 1, perPage = 10))
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is CivilDefenseUC.Success.CivilDefenseSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(civilDefense = ResultState.Success(it.general))
                        }
                    }
                }
            }
            .onFailure<CivilDefenseUC.Failure> {
                when (it) {
                    is CivilDefenseUC.Failure.CivilDefenseError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                civilDefense = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "Error al cargar los servicios de defensa civil"
                                ),
                            )
                        }
                    }
                }
            }
    }

    private fun fetchPolice() {
        Timber.d("Fetching police service details")
        // Implement fetching logic for police service
    }

    private fun fetchFirefighter() {
        Timber.d("Fetching firefighter service details")
        // Implement fetching logic for firefighter service
    }

    private suspend fun fetchLocalSecurity() {
        updateUiStateOnMain { it.copy(securityCase = true) }
        Timber.d("Fetchin0g local security service details")
        // Implement fetching logic for local security service
    }


    suspend fun getTypeService(type: String?) {
        Timber.d("getTypeService: $type")
        typeService = type ?: ""
        when (typeService) {
            "general" -> fetchGeneral()
            "civil_defense" -> fetchCivilDefense()
            "police" -> fetchPolice()
            "firefighter" -> fetchFirefighter()
            "local_security" -> fetchLocalSecurity()
            else -> Timber.w("Unknown service type: $typeService")
        }
    }
}