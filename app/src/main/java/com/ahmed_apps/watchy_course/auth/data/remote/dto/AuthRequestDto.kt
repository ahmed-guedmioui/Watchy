package com.ahmed_apps.watchy_course.auth.data.remote.dto

data class AuthRequestDto(
    val name: String = "",
    val email: String,
    val password: String = ""
)
