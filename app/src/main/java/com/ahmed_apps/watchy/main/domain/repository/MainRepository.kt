package com.ahmed_apps.watchy.main.domain.repository

import com.ahmed_apps.watchy.main.domain.model.Media
import com.ahmed_apps.watchy.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * @author Ahmed Guedmioui
 */
interface MainRepository {

    suspend fun upsertMediaList(mediaList: List<Media>)

    suspend fun upsertMediaItem(media: Media)

    suspend fun getMediaListByCategory(
        category: String
    ): List<Media>

    suspend fun getMediaById(id: Int): Media

    suspend fun getMediaListByIds(
        ids: List<Int>
    ): List<Media>

    suspend fun getTrending(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int
    ): Flow<Resource<List<Media>>>

    suspend fun getMoviesAndTv(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int
    ): Flow<Resource<List<Media>>>

    suspend fun cleareMainDb()

}




















