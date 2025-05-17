package com.devpaul.auth.ui.register

import com.devpaul.core_data.util.Constant
import com.devpaul.core_data.util.Constant.LOG_IN_KEY
import com.devpaul.core_data.viewmodel.StatelessViewModel
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.koin.android.annotation.KoinViewModel
import javax.inject.Inject

@KoinViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dataStoreUseCase: DataStoreUseCase,
) : StatelessViewModel<RegisterUiEvent, RegisterUiIntent>() {

    override fun handleIntent(intent: RegisterUiIntent) {
        when (intent) {
            is RegisterUiIntent.Register -> register(
                name = intent.name,
                lastName = intent.lastname,
                email = intent.email,
                password = intent.password,
            )
        }
    }

    fun register(
        name: String,
        lastName: String,
        email: String,
        password: String,
    ) {
        setLoading(true)
        executeInScope(
            block = {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val userId = result.user?.uid
                if (userId != null) {
                    val user = hashMapOf(
                        Constant.ID_FIELD to userId,
                        Constant.NAME_FIELD to name,
                        Constant.LAST_NAME_FIELD to lastName,
                        Constant.EMAIL_FIELD to email,
                        Constant.PASSWORD_FIELD to password
                    )
                    firestore.collection(Constant.USERS_COLLECTION).document(userId).set(user).await()
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