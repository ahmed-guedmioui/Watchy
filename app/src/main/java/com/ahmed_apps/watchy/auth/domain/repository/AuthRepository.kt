package com.ahmed_apps.watchy.auth.domain.repository

import com.ahmed_apps.watchy.auth.util.AuthResult

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

















