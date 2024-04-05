package com.ahmed_apps.watchy.auth.domain.usecase

/**
 * @author Ahmed Guedmioui
 */
data class FormValidatorUseCase(
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateNameUseCase: ValidateNameUseCase
)
