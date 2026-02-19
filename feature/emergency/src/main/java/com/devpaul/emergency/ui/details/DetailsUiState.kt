package com.devpaul.emergency.ui.details

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.emergency.domain.entity.GeneralEntity
import com.devpaul.emergency.domain.entity.PoliceEntity
import com.devpaul.emergency.ui.details.components.Region

data class DetailsUiState(
    val generalCase: Boolean = false,
    val general: ResultState<GeneralEntity> = ResultState.Loading,

    val civilCase : Boolean = false,
    val civilDefense: ResultState<GeneralEntity> = ResultState.Loading,

    val securityCase : Boolean = false,

    val policeCase : Boolean = false,
    val police : ResultState<PoliceEntity> = ResultState.Loading,
    val selectedPoliceRegion: Region = Region.Lima,

    val limaSecurity: ResultState<GeneralEntity> = ResultState.Loading,
    val provinceSecurity: ResultState<GeneralEntity> = ResultState.Loading,
)