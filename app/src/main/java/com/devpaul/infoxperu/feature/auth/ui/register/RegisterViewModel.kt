package com.devpaul.infoxperu.feature.auth.ui.register

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.StatelessViewModel
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRegister
import com.devpaul.infoxperu.feature.auth.domain.usecase.RegisterUseCase
import com.devpaul.infoxperu.feature.util.Constant
import com.devpaul.infoxperu.feature.util.Constant.LOG_IN_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase,
    private val registerUseCase: RegisterUseCase,
) : StatelessViewModel<RegisterUiEvent, RegisterUiIntent>() {

    override fun handleIntent(intent: RegisterUiIntent) {
        when (intent) {
            is RegisterUiIntent.Register -> register(
                name = intent.name,
                lastname = intent.lastname,
                email = intent.email,
                password = intent.password,
            )
        }
    }

    fun register(
        name: String,
        lastname: String,
        email: String,
        password: String,
    ) {
        setLoading(true)
        executeInScope(
            block = {
                val requestRegister =
                    RequestRegister(
                        name = name,
                        lastname = lastname,
                        email = email,
                        password = password
                    )
                val result = registerUseCase(RegisterUseCase.Params(requestRegister))
                if (result is ResultState.Success) {
                    dataStoreUseCase.setValue(LOG_IN_KEY, true)
                    setUiEvent(RegisterUiEvent.RegisterSuccess(message = Constant.REGISTER_SUCCESS))
                } else {
                    setUiEvent(RegisterUiEvent.RegisterError(error = Constant.REGISTER_ERROR))
                }
            },
            onError = { error ->
                setUiEvent(RegisterUiEvent.RegisterError(error = "${Constant.REGISTER_FAILURE} ${error.message}"))
            },
            onComplete = {
                setLoading(false)
            }
        )
    }
}
