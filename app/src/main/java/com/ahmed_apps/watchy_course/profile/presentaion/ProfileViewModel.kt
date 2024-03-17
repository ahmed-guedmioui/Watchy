package com.ahmed_apps.watchy_course.profile.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed_apps.watchy_course.auth.domain.repository.AuthRepository
import com.ahmed_apps.watchy_course.auth.util.AuthResult
import com.ahmed_apps.watchy_course.profile.domain.repository.ProfileRepository
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
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    private val _logoutResultChannel = Channel<AuthResult<Unit>>()
    val logoutResultChannel = _logoutResultChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            _profileState.update {
                it.copy(
                    name = profileRepository.getName(),
                    email = profileRepository.getEmail()
                )
            }
        }
    }

    fun onEvent(profileUiEvent: ProfileUiEvents) {
        when (profileUiEvent) {
            ProfileUiEvents.Logout -> {
                viewModelScope.launch {
                    _logoutResultChannel.send(
                        authRepository.logout()
                    )
                }
            }
        }
    }

}


















