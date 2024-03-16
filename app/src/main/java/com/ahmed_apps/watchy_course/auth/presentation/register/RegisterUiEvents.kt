package com.ahmed_apps.watchy_course.auth.presentation.register

/**
 * @author Ahmed Guedmioui
 */
sealed class RegisterUiEvents {

    data class OnNameChanged(
        val newName: String
    ): RegisterUiEvents()

    data class OnEmailChanged(
        val newEmail: String
    ): RegisterUiEvents()

    data class OnPasswordChanged(
        val newPassword: String
    ): RegisterUiEvents()

    object Register: RegisterUiEvents()

}
















