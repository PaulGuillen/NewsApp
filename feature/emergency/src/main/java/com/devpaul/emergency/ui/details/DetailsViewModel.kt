package com.devpaul.emergency.ui.details

import com.devpaul.core_domain.entity.Output
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.emergency.domain.usecase.CivilDefenseUC
import com.devpaul.emergency.domain.usecase.GeneralUC
import com.devpaul.emergency.domain.usecase.PoliceUC
import com.devpaul.emergency.ui.details.components.Region
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class DetailsViewModel(
    private val generalUC: GeneralUC,
    private val civilDefenseUC: CivilDefenseUC,
    private val policeUC: PoliceUC,
) : StatefulViewModel<DetailsUiState, DetailsUiIntent, DetailsUiEvent>(
    defaultUIState = {
        DetailsUiState()
    }
) {

    private var typeService: String = ""

    override suspend fun onUiIntent(intent: DetailsUiIntent) {
        when (intent) {
            is DetailsUiIntent.CallNumber -> callNumber(intent.phone)
            is DetailsUiIntent.CallGeneralService -> fetchGeneral()
            is DetailsUiIntent.ChangePoliceRegion -> changePoliceRegion(intent.region)
        }
    }

    private fun callNumber(phone: String) {
        val clean = phone.filter { it.isDigit() }
        val formatted = when {
            clean.length == 9 && clean.startsWith("9") -> "+51$clean"
            clean.length <= 5 -> clean
            else -> clean
        }
        DetailsUiEvent.RequireCallPermission(formatted).send()
    }

    private suspend fun fetchGeneral() {
        updateUiStateOnMain { it.copy(generalCase = true) }
        updateUiStateOnMain { it.copy(general = ResultState.Loading) }

        when (val result = generalUC.generalService()) {
            is Output.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(general = ResultState.Success(result.data))
                }
            }

            is Output.Failure -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        general = ResultState.Error(
                            message = result.error.message
                                ?: "Error al cargar los servicios generales"
                        )
                    )
                }
            }
        }
    }

    private suspend fun changePoliceRegion(region: Region) {
        updateUiStateOnMain { it.copy(selectedPoliceRegion = region) }
        Timber.d("Changing police region to: $region")
        val type = when (region) {
            Region.Lima -> "police_lima"
            Region.Provincias -> "police_provinces"
        }
        fetchPolice(type)
    }

    private suspend fun fetchCivilDefense() {
        updateUiStateOnMain { it.copy(civilCase = true) }
        updateUiStateOnMain { it.copy(civilDefense = ResultState.Loading) }

        when (val result = civilDefenseUC.civilDefenseService()) {
            is Output.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(civilDefense = ResultState.Success(result.data))
                }
            }

            is Output.Failure -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        civilDefense = ResultState.Error(
                            message = result.error.message
                                ?: "Error al cargar los servicios generales"
                        )
                    )
                }
            }
        }
    }

    private suspend fun fetchPolice(type: String) {
        val region = if (type == "police_lima") Region.Lima else Region.Provincias

        updateUiStateOnMain {
            it.copy(
                policeCase = true,
                police = ResultState.Loading,
                selectedPoliceRegion = region //
            )
        }

        when (val result = policeUC.policeService(type)) {
            is Output.Success -> updateUiStateOnMain { it.copy(police = ResultState.Success(result.data)) }
            is Output.Failure -> updateUiStateOnMain {
                it.copy(police = ResultState.Error(result.error.message ?: "Error al cargar los servicios generales"))
            }
        }
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
            "police" -> fetchPolice("police_lima")
            "firefighter" -> fetchFirefighter()
            "local_security" -> fetchLocalSecurity()
            else -> Timber.w("Unknown service type: $typeService")
        }
    }
}