package com.ahmed_apps.watchy.auth.presentation.login

/**
 * @author Ahmed Guedmioui
 */
data class LoginState(

    val isLoading: Boolean = false,

    val email: String = "",
    val password: String = ""

)
