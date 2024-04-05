package com.ahmed_apps.watchy.main.data.repository

import android.app.Application
import com.ahmed_apps.watchy.R
import com.ahmed_apps.watchy.favorites.domain.repository.FavoritesRepository
import com.ahmed_apps.watchy.main.data.local.MediaDatabase
import com.ahmed_apps.watchy.main.data.mappers.toMedia
import com.ahmed_apps.watchy.main.data.mappers.toMediaEntity
import com.ahmed_apps.watchy.main.data.remote.api.MediaApi
import com.ahmed_apps.watchy.main.domain.model.Media
import com.ahmed_apps.watchy.main.domain.repository.MainRepository
import com.ahmed_apps.watchy.util.APIConstants.MOVIE
import com.ahmed_apps.watchy.util.APIConstants.POPULAR
import com.ahmed_apps.watchy.util.APIConstants.TRENDING
import com.ahmed_apps.watchy.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * @author Ahmed Guedmioui
 */
class MainRepositoryImpl @Inject constructor(
    private val application: Application,
    private val mediaApi: MediaApi,
    mediaDatabase: MediaDatabase,
    private val favoritesRepository: FavoritesRepository
) : MainRepository {

    private val mediaDao = mediaDatabase.mediaDao

    override suspend fun upsertMediaList(mediaList: List<Media>) {
        mediaDao.upsertMediaList(mediaList.map { it.toMediaEntity() })
    }

    override suspend fun upsertMediaItem(media: Media) {
        mediaDao.upsertMediaItem(media.toMediaEntity())
    }

    override suspend fun getMediaListByCategory(category: String): List<Media> {
        return mediaDao.getMediaListByCategory(category).map { it.toMedia() }
    }

    override suspend fun getMediaById(id: Int): Media {
        return mediaDao.getMediaItemById(id).toMedia()
    }

    override suspend fun getMediaListByIds(ids: List<Int>): List<Media> {
        return mediaDao.getMediaListByIds(ids).map { it.toMedia() }
    }

    override suspend fun getTrending(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        time: String,
        page: Int
    ): Flow<Resource<List<Media>>> {
        return flow {

            emit(Resource.Loading(true))

            val localMediaList = mediaDao.getMediaListByCategory(TRENDING)

            val loadJustFromCache =
                localMediaList.isNotEmpty() && !forceFetchFromRemote && !isRefresh

            if (loadJustFromCache) {
                emit(Resource.Success(localMediaList.map { it.toMedia() }))
                emit(Resource.Loading(false))
                return@flow
            }


            val remoteMediaList = try {
                mediaApi.getTrending(
                    type, time, if (isRefresh) 1 else page
                )?.results
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.couldn_t_load_data)))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.couldn_t_load_data)))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.couldn_t_load_data)))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteMediaList?.let { mediaDtos ->
                val entities = mediaDtos.map { mediaDto ->

                    val favoriteMedia =
                        favoritesRepository.getMediaItemById(
                            mediaDto.id ?: 0
                        )

                    mediaDto.toMediaEntity(
                        type = mediaDto.media_type ?: MOVIE,
                        category = TRENDING,
                        isLiked = favoriteMedia?.isLiked ?: false,
                        isBookmarked = favoriteMedia?.isBookmarked ?: false,
                    )
                }

                if (isRefresh) {
                    mediaDao.deleteAllMediaListByCategory(TRENDING)
                }

                mediaDao.upsertMediaList(entities)

                emit(Resource.Success(entities.map { it.toMedia() }))
                emit(Resource.Loading(false))
                return@flow
            }


            emit(Resource.Error(application.getString(R.string.couldn_t_load_data)))
            emit(Resource.Loading(false))
        }

    }

    override suspend fun getMoviesAndTv(
        forceFetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int
    ): Flow<Resource<List<Media>>> {
        return flow {

            emit(Resource.Loading(true))

            val localMediaList = mediaDao.getMediaListByTypeAndCategory(type, POPULAR)

            val loadJustFromCache =
                localMediaList.isNotEmpty() && !forceFetchFromRemote && !isRefresh

            if (loadJustFromCache) {
                emit(Resource.Success(localMediaList.map { it.toMedia() }))
                emit(Resource.Loading(false))
                return@flow
            }


            val remoteMediaList = try {
                mediaApi.getMoviesAndTv(
                    type, POPULAR, if (isRefresh) 1 else page
                )?.results
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.couldn_t_load_data)))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.couldn_t_load_data)))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.couldn_t_load_data)))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteMediaList?.let { mediaDtos ->
                val entities = mediaDtos.map { mediaDto ->
                    mediaDto.toMediaEntity(
                        type, POPULAR,
                        isLiked = false,
                        isBookmarked = false,
                    )
                }

                if (isRefresh) {
                    mediaDao.deleteAllMediaListByTypeAndCategory(type, POPULAR)
                }

                mediaDao.upsertMediaList(entities)

                emit(Resource.Success(entities.map { it.toMedia() }))
                emit(Resource.Loading(false))
                return@flow
            }


            emit(Resource.Error(application.getString(R.string.couldn_t_load_data)))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun cleareMainDb() {
        mediaDao.deleteAllMediaItem()
    }
}

















