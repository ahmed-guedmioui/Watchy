package com.ahmed_apps.watchy.details.data.repository

import android.app.Application
import com.ahmed_apps.watchy.R
import com.ahmed_apps.watchy.details.data.remote.api.DetailsApi
import com.ahmed_apps.watchy.details.domain.repository.SimilarRepository
import com.ahmed_apps.watchy.favorites.domain.repository.FavoritesRepository
import com.ahmed_apps.watchy.main.data.mappers.toMedia
import com.ahmed_apps.watchy.main.data.remote.dto.MediaDto
import com.ahmed_apps.watchy.main.domain.model.Media
import com.ahmed_apps.watchy.main.domain.repository.MainRepository
import com.ahmed_apps.watchy.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * @author Ahmed Guedmioui
 */
class SimilarRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi,
    private val mainRepository: MainRepository,
    private val application: Application,
    private val favoritesRepository: FavoritesRepository
) : SimilarRepository {
    override suspend fun getSimilarMedia(
        id: Int
    ): Flow<Resource<List<Media>>> {
        return flow {
            emit(Resource.Loading(true))

            val media = mainRepository.getMediaById(id)
            val localSimilarList = mainRepository.getMediaListByIds(
                media.similarMediaIds
            )

            if (localSimilarList.isNotEmpty()) {
                emit(Resource.Success(localSimilarList))
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteSimilarList = getRemoteSimilarList(
                type = media.mediaType,
                id = id
            )

            remoteSimilarList?.let { similarMediaDtos ->
                val similarIds = similarMediaDtos.map { it.id ?: 0 }
                mainRepository.upsertMediaItem(
                    media.copy(
                        similarMediaIds = similarIds
                    )
                )

                val similarMedia = similarMediaDtos.map { mediaDto ->

                    val favoriteMedia =
                        favoritesRepository.getMediaItemById(
                            mediaDto.id ?: 0
                        )

                    mediaDto.toMedia(
                        media.category,
                        isLiked = favoriteMedia?.isLiked ?: false,
                        isBookmarked = favoriteMedia?.isBookmarked ?: false,
                    )
                }

                mainRepository.upsertMediaList(similarMedia)

                emit(
                    Resource.Success(
                        mainRepository.getMediaListByIds(similarIds)
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            emit(
                Resource.Error(
                    application.getString(R.string.couldn_t_load_data)
                )
            )
            emit(Resource.Loading(false))
        }
    }

    private suspend fun getRemoteSimilarList(
        type: String,
        id: Int
    ): List<MediaDto>? {

        val remoteDetails = try {
            detailsApi.getSimilarMediaList(
                type, id, 1
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return remoteDetails?.results
    }

}




















