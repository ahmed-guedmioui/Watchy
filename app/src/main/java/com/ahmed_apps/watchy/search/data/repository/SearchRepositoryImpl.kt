package com.ahmed_apps.watchy.search.data.repository

import android.app.Application
import com.ahmed_apps.watchy.R
import com.ahmed_apps.watchy.favorites.domain.repository.FavoritesRepository
import com.ahmed_apps.watchy.main.data.mappers.toMedia
import com.ahmed_apps.watchy.main.domain.model.Media
import com.ahmed_apps.watchy.search.data.remote.api.SearchApi
import com.ahmed_apps.watchy.search.domain.repository.SearchRepository
import com.ahmed_apps.watchy.util.APIConstants
import com.ahmed_apps.watchy.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * @author Ahmed Guedmioui
 */
class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi,
    private val application: Application,
    private val favoritesRepository: FavoritesRepository
) : SearchRepository {
    override suspend fun getSearchList(
        query: String, page: Int
    ): Flow<Resource<List<Media>>> {
        return flow {

            emit(Resource.Loading(true))

            val remoteSearchList = try {
                searchApi.getSearchList(
                    query, page
                )?.results
            } catch (e: IOException) {
                e.printStackTrace()
                emit(
                    Resource.Error(application.getString(R.string.couldn_t_load_data))
                )
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(
                    Resource.Error(application.getString(R.string.couldn_t_load_data))
                )
                emit(Resource.Loading(false))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    Resource.Error(application.getString(R.string.couldn_t_load_data))
                )
                emit(Resource.Loading(false))
                return@flow
            }

            remoteSearchList?.let { mediaDtos ->
                val mediaList = mediaDtos.map { mediaDto ->

                    val favoriteMedia =
                        favoritesRepository.getMediaItemById(
                            mediaDto.id ?: 0
                        )

                    mediaDto.toMedia(
                        APIConstants.POPULAR,
                        isLiked = favoriteMedia?.isLiked ?: false,
                        isBookmarked = favoriteMedia?.isBookmarked ?: false,
                    )
                }

                emit(Resource.Success(mediaList))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(
                Resource.Error(application.getString(R.string.couldn_t_load_data))
            )
            emit(Resource.Loading(false))
        }
    }


}





















