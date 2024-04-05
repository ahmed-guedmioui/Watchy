package com.ahmed_apps.watchy.auth.domain.usecase

/**
 * @author Ahmed Guedmioui
 */
class ValidateNameUseCase {

    operator fun invoke(name: String): Boolean {
        return name.length in 4..50
    }

}