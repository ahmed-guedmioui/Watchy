package com.ahmed_apps.watchy.favorites.data.remote.dto.request

data class UpsertMediaRequest(
    val mediaRequest: MediaRequest,
    val email: String,
)