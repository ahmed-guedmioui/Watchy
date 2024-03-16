package com.ahmed_apps.watchy_course.auth.domain.repository

import com.ahmed_apps.watchy_course.auth.util.AuthResult

/**
 * @author Ahmed Guedmioui
 */
interface AuthRepository {

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): AuthResult<Unit>

    suspend fun login(
        email: String,
        password: String
    ): AuthResult<Unit>

    suspend fun authenticate(): AuthResult<Unit>

    suspend fun logout(): AuthResult<Unit>

}

















