package com.ahmed_apps.watchy_course.main.data.repository

import android.app.Application
import com.ahmed_apps.watchy_course.main.data.local.MediaDatabase
import com.ahmed_apps.watchy_course.main.data.local.MediaEntity
import com.ahmed_apps.watchy_course.main.data.remote.api.MediaApi
import com.ahmed_apps.watchy_course.main.domain.model.Media
import com.ahmed_apps.watchy_course.main.domain.repository.MainRepository
import com.ahmed_apps.watchy_course.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * @author Ahmed Guedmioui
 */
class MainRepositoryImpl(
    private val application: Application,
    private val mediaApi: MediaApi,
    mediaDatabase: MediaDatabase
): MainRepository {

    private val mediaDao = mediaDatabase.mediaDao

    override suspend fun upsertMediaList(mediaEntities: List<MediaEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertMediaItem(mediaEntity: MediaEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getMediaListByCategory(category: String): List<MediaEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrending(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int
    ): Flow<Resource<List<Media>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMoviesAndTv(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int
    ): Flow<Resource<List<Media>>> {
        TODO("Not yet implemented")
    }
}