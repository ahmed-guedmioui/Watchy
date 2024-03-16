package com.ahmed_apps.watchy_course.auth.data.remote

import com.ahmed_apps.watchy_course.auth.data.remote.dto.AuthRequestDto
import com.ahmed_apps.watchy_course.util.BackendConstants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @author Ahmed Guedmioui
 */
interface AuthApi {

    @POST(BackendConstants.REGISTER)
    suspend fun register(
        @Body authRequestDto: AuthRequestDto
    )

    @POST(BackendConstants.LOGIN)
    suspend fun login(
        @Body authRequestDto: AuthRequestDto
    )

    @GET(BackendConstants.AUTHENTICATE)
    suspend fun authenticate(
        @Body authRequestDto: AuthRequestDto
    )

}
















