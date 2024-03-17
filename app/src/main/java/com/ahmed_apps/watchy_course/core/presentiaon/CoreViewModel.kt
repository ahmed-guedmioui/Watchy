package com.ahmed_apps.watchy_course.core.presentiaon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed_apps.watchy_course.auth.domain.repository.AuthRepository
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
class CoreViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _authResultChannel = Channel<AuthResult<Unit>>()
    val authResultChannel = _authResultChannel.receiveAsFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        authenticate()
    }

    private fun authenticate() {
        viewModelScope.launch {
            _isLoading.update { true }
            val result = authRepository.authenticate()
            _authResultChannel.send(result)
            _isLoading.update { false }
        }
    }

}






















