package com.ahmed_apps.watchy_course.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed_apps.watchy_course.auth.di.AuthRepositoryModule
import com.ahmed_apps.watchy_course.auth.domain.repository.AuthRepository
import com.ahmed_apps.watchy_course.auth.domain.usecase.FormValidatorUseCase
import com.ahmed_apps.watchy_course.auth.util.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Ahmed Guedmioui
 */

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val formValidatorUseCase: FormValidatorUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    private val _authResultChannel = Channel<AuthResult<Unit>>()
    val authResultChannel = _authResultChannel.receiveAsFlow()

    private val _invalidCredentialsChannel = Channel<Boolean>()
    val invalidCredentialsChannel = _invalidCredentialsChannel.receiveAsFlow()


    fun onEvent(registerUiEvent: RegisterUiEvents) {
        when (registerUiEvent) {
            is RegisterUiEvents.OnEmailChanged -> {
                _registerState.update {
                    it.copy(email = registerUiEvent.newEmail)
                }
            }

            is RegisterUiEvents.OnNameChanged -> {
                _registerState.update {
                    it.copy(email = registerUiEvent.newName)
                }
            }

            is RegisterUiEvents.OnPasswordChanged -> {
                _registerState.update {
                    it.copy(email = registerUiEvent.newPassword)
                }
            }

            RegisterUiEvents.Register -> {
                val isNameValid =
                    formValidatorUseCase.validateNameUseCase.invoke(
                        registerState.value.name
                    )

                val isEmailValid =
                    formValidatorUseCase.validateEmailUseCase.invoke(
                        registerState.value.email
                    )

                val isPasswordValid =
                    formValidatorUseCase.validatePasswordUseCase.invoke(
                        registerState.value.password
                    )

                if (isNameValid && isEmailValid && isPasswordValid) {
                    register()
                } else {
                    viewModelScope.launch {
                        _invalidCredentialsChannel.send(true)
                    }
                }
            }
        }
    }

    private fun register() {

        _registerState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val result = authRepository.register(
                registerState.value.name,
                registerState.value.email,
                registerState.value.password
            )

            _authResultChannel.send(result)

            _registerState.update {
                it.copy(isLoading = false)
            }
        }
    }

}






















