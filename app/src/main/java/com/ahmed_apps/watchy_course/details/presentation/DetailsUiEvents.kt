package com.ahmed_apps.watchy_course.details.presentation

/**
 * @author Ahmed Guedmioui
 */
sealed class DetailsUiEvents {
    data class StartLoading(
        val mediaId: Int
    ): DetailsUiEvents()

    object Refresh: DetailsUiEvents()
    object NavigateToWatchVideo: DetailsUiEvents()

}



















