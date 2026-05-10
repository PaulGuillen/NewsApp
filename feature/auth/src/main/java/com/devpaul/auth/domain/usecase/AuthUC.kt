package com.devpaul.auth.domain.usecase

import com.devpaul.auth.domain.entity.Auth
import com.devpaul.auth.domain.entity.Register
import com.devpaul.auth.domain.repository.AuthRepository
import com.devpaul.core_domain.entity.Output
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import org.koin.core.annotation.Factory

@Factory
class AuthUC(
    private val authRepository: AuthRepository,
) {

    suspend fun login(
        email: String,
        password: String,
    ): Output<Auth, Failure> {
        return try {
            val login = authRepository.login(email, password)
            Output.Success(login)
        } catch (ex: Exception) {
            Output.Failure(mapAuthException(ex))
        }
    }

    suspend fun register(
        name: String,
        lastname: String,
        email: String,
        password: String,
    ): Output<Register, Failure> {
        return try {
            val register = authRepository.register(
                name = name,
                lastname = lastname,
                email = email,
                password = password,
            )
            Output.Success(register)
        } catch (ex: Exception) {
            Output.Failure(mapAuthException(ex))
        }
    }

    suspend fun recoverPassword(
        email: String,
    ): Output<Unit, Failure> {
        return try {
            authRepository.recoverPassword(email)
            Output.Success(Unit)
        } catch (ex: Exception) {
            Output.Failure(mapAuthException(ex))
        }
    }

    sealed class Failure {
        data object InvalidCredentials : Failure()
        data object EmailAlreadyInUse : Failure()
        data object InvalidUser : Failure()
        data object Network : Failure()
        data class Unknown(val throwable: Throwable) : Failure()
    }

    private fun mapAuthException(ex: Exception): Failure {
        return when (ex) {
            is FirebaseAuthInvalidCredentialsException -> Failure.InvalidCredentials
            is FirebaseAuthUserCollisionException -> Failure.EmailAlreadyInUse
            is FirebaseAuthInvalidUserException -> Failure.InvalidUser
            is FirebaseNetworkException -> Failure.Network
            else -> Failure.Unknown(ex)
        }
    }
}