package com.ahmed_apps.watchy_course.auth.domain.usecase

/**
 * @author Ahmed Guedmioui
 */
class ValidateNameUseCase {

    operator fun invoke(name: String): Boolean {
        return name.length in 4..50
    }

}