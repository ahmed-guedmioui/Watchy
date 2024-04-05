package com.ahmed_apps.watchy.details.domain.repository

import com.ahmed_apps.watchy.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * @author Ahmed Guedmioui
 */
interface VideosRepository {
    suspend fun getVideos(
        id: Int,
        isRefreshing: Boolean
    ): Flow<Resource<List<String>>>
}
















