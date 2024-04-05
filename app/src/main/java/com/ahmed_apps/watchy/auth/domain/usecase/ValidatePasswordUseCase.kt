package com.ahmed_apps.watchy.auth.domain.usecase

/**
 * @author Ahmed Guedmioui
 */
class ValidatePasswordUseCase {

    operator fun invoke(password: String): Boolean {
        return password.length >= 8 &&
                password.any { it.isUpperCase() } &&
                password.any { it.isDigit() }
    }

}




















