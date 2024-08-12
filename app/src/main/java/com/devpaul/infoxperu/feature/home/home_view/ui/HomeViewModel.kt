package com.devpaul.infoxperu.feature.home.home_view.ui

import androidx.lifecycle.ViewModel
import com.devpaul.infoxperu.feature.home.home_view.uc.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {

}
