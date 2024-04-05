package com.ahmed_apps.watchy.details.domain.repository

import com.ahmed_apps.watchy.main.domain.model.Media
import com.ahmed_apps.watchy.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * @author Ahmed Guedmioui
 */
interface SimilarRepository {
    suspend fun getSimilarMedia(
        id: Int
    ): Flow<Resource<List<Media>>>
}
















