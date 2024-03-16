package com.ahmed_apps.watchy_course.auth.data.remote.dto

data class AuthRequest(
    val name: String = "",
    val email: String,
    val password: String = ""
)
