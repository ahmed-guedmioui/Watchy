package com.ahmed_apps.watchy_course.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val formValidatorUseCase: FormValidatorUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    private val _authResultChannel = Channel<AuthResult<Unit>>()
    val authResultChannel = _authResultChannel.receiveAsFlow()

    private val _invalidCredentialsChannel = Channel<Boolean>()
    val invalidCredentialsChannel = _invalidCredentialsChannel.receiveAsFlow()


    fun onEvent(loginUiEvent: LoginUiEvents) {
        when (loginUiEvent) {
            is LoginUiEvents.OnEmailChanged -> {
                _loginState.update {
                    it.copy(email = loginUiEvent.newEmail)
                }
            }

            is LoginUiEvents.OnPasswordChanged -> {
                _loginState.update {
                    it.copy(password = loginUiEvent.newPassword)
                }
            }

            LoginUiEvents.Login -> {

                val isEmailValid =
                    formValidatorUseCase.validateEmailUseCase.invoke(
                        loginState.value.email
                    )

                val isPasswordValid =
                    formValidatorUseCase.validatePasswordUseCase.invoke(
                        loginState.value.password
                    )

                if (isEmailValid && isPasswordValid) {
                    login()
                } else {
                    viewModelScope.launch {
                        _invalidCredentialsChannel.send(true)
                    }
                }
            }
        }
    }

    private fun login() {

        viewModelScope.launch {
            _loginState.update {
                it.copy(isLoading = true)
            }

            val result = authRepository.login(
                loginState.value.email,
                loginState.value.password
            )

            _authResultChannel.send(result)

            _loginState.update {
                it.copy(isLoading = false)
            }
        }
    }

}






















