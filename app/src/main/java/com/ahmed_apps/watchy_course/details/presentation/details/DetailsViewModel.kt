package com.ahmed_apps.watchy_course.details.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed_apps.watchy_course.details.domain.repository.DetailsRepository
import com.ahmed_apps.watchy_course.details.domain.repository.SimilarRepository
import com.ahmed_apps.watchy_course.details.domain.repository.VideosRepository
import com.ahmed_apps.watchy_course.details.domain.usecase.MinutesToReadableTime
import com.ahmed_apps.watchy_course.favorites.domain.repository.FavoritesRepository
import com.ahmed_apps.watchy_course.main.domain.repository.MainRepository
import com.ahmed_apps.watchy_course.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Ahmed Guedmioui
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val detailsRepository: DetailsRepository,
    private val videosRepository: VideosRepository,
    private val similarRepository: SimilarRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()

    private val _navigateToWatchVideoChannel = Channel<Boolean>()
    val navigateToWatchVideoChannel = _navigateToWatchVideoChannel.receiveAsFlow()

    fun onEvent(detailsUiEvent: DetailsUiEvents) {
        when (detailsUiEvent) {
            is DetailsUiEvents.StartLoading -> {
                loadMediaItem(
                    id = detailsUiEvent.mediaId
                )
            }

            DetailsUiEvents.NavigateToWatchVideo -> {
                viewModelScope.launch {
                    if (detailsState.value.videoId.isNotEmpty()) {
                        _navigateToWatchVideoChannel.send(true)
                    } else {
                        _navigateToWatchVideoChannel.send(false)
                    }
                }
            }

            DetailsUiEvents.Refresh -> {
                loadMediaItem(isRefresh = true)
            }

            DetailsUiEvents.BookmarkOrUnBookmark -> {
                bookmarkOrUnBookmark()
            }

            DetailsUiEvents.LikeOrDislike -> {
                likeOrDislike()
            }

            is DetailsUiEvents.ShowOrHideAlertDialog -> {
                val media = detailsState.value.media

                if (detailsUiEvent.alertDialogType == 1 && media?.isLiked == false) {
                    likeOrDislike()
                    return
                }

                if (detailsUiEvent.alertDialogType == 2 && media?.isBookmarked == false) {
                    bookmarkOrUnBookmark()
                    return
                }

                _detailsState.update {
                    it.copy(
                        showAlertDialog = !it.showAlertDialog,
                        alertDialogType = detailsUiEvent.alertDialogType
                    )
                }

            }
        }
    }

    private fun loadMediaItem(
        isRefresh: Boolean = false,
        id: Int = detailsState.value.media?.mediaId ?: 0
    ) {

        viewModelScope.launch {
            _detailsState.update {
                it.copy(
                    media = mainRepository.getMediaById(id)
                )
            }

            viewModelScope.launch {
                loadDetails(isRefresh)
                loadVideos(isRefresh)
                loadSimilar()
            }
        }
    }

    private suspend fun loadDetails(
        isRefresh: Boolean
    ) {
        detailsRepository.getDetails(
            id = detailsState.value.media?.mediaId ?: 0,
            isRefreshing = isRefresh
        ).collect { result ->
            when (result) {
                is Resource.Error -> Unit

                is Resource.Loading -> Unit

                is Resource.Success -> {
                    result.data?.let { media ->
                        _detailsState.update {
                            it.copy(
                                media = it.media?.copy(
                                    runTime = media.runTime,
                                    tagLine = media.tagLine
                                ),
                                readableTime = if (media.runTime != 0) {
                                    MinutesToReadableTime(media.runTime).invoke()
                                } else ""
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun loadVideos(
        isRefresh: Boolean
    ) {
        videosRepository.getVideos(
            id = detailsState.value.media?.mediaId ?: 0,
            isRefreshing = isRefresh
        ).collect { result ->
            when (result) {
                is Resource.Error -> Unit

                is Resource.Loading -> Unit

                is Resource.Success -> {
                    result.data?.let { videoList ->
                        _detailsState.update {
                            it.copy(
                                videoList = videoList,
                                videoId = if (videoList.isNotEmpty()) {
                                    videoList.shuffled()[0]
                                } else {
                                    ""
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun loadSimilar() {
        similarRepository.getSimilarMedia(
            id = detailsState.value.media?.mediaId ?: 0
        ).collect { result ->
            when (result) {
                is Resource.Error -> Unit

                is Resource.Loading -> Unit

                is Resource.Success -> {
                    result.data?.let { similarMediaList ->
                        _detailsState.update {
                            it.copy(
                                similarList = similarMediaList
                            )
                        }
                    }
                }
            }
        }
    }

    private fun likeOrDislike() {
        _detailsState.update {
            it.copy(
                media = it.media?.copy(
                    isLiked = !it.media.isLiked
                ),
                alertDialogType = 0,
                showAlertDialog = false
            )
        }
        updateOrDeleteMedia()
    }

    private fun bookmarkOrUnBookmark() {
        _detailsState.update {
            it.copy(
                media = it.media?.copy(
                    isBookmarked = !it.media.isBookmarked
                ),
                alertDialogType = 0,
                showAlertDialog = false
            )
        }
        updateOrDeleteMedia()
    }

    private fun updateOrDeleteMedia() {
        viewModelScope.launch {
            detailsState.value.media?.let { media ->
                if (!media.isLiked && !media.isBookmarked) {
                    favoritesRepository.deleteFavoritesMediaItem(
                        media
                    )
                } else {
                    mainRepository.upsertMediaItem(
                        media
                    )
                    favoritesRepository.upsetFavoritesMediaItem(
                        media
                    )
                }
            }
        }
    }

}






















