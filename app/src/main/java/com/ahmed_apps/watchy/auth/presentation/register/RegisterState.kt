package com.ahmed_apps.watchy.auth.presentation.register

/**
 * @author Ahmed Guedmioui
 */
data class RegisterState(

    val isLoading: Boolean = false,

    val name: String = "",
    val email: String = "",
    val password: String = ""

)
