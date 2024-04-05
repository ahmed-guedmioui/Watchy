package com.ahmed_apps.watchy.favorites.domain.repository

import com.ahmed_apps.watchy.favorites.data.remote.dto.request.MediaRequest
import com.ahmed_apps.watchy.favorites.data.remote.dto.respond.BackendMediaDto

/**
 * @author Ahmed Guedmioui
 */
interface BackendFavoritesRepository {

    suspend fun getLikedMediaList(): List<BackendMediaDto>?
    suspend fun getBookmarkedMediaList(): List<BackendMediaDto>?

    suspend fun getMediaById(
        mediaId: Int
    ): BackendMediaDto?

    suspend fun upsertMediaToUser(
        mediaRequest: MediaRequest
    ): Boolean

    suspend fun deleteMediaFromUser(
       mediaId: Int
    ): Boolean

}

















