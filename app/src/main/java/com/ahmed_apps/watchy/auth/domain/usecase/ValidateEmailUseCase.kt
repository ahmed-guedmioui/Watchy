package com.ahmed_apps.watchy.auth.domain.usecase

import android.util.Patterns

/**
 * @author Ahmed Guedmioui
 */
class ValidateEmailUseCase {

    operator fun invoke(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(
            email
        ).matches()
    }

}