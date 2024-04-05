package com.ahmed_apps.watchy.details.presentation.details

/**
 * @author Ahmed Guedmioui
 */
sealed class DetailsUiEvents {
    data class StartLoading(
        val mediaId: Int
    ): DetailsUiEvents()

    object Refresh: DetailsUiEvents()
    object NavigateToWatchVideo: DetailsUiEvents()


    data class ShowOrHideAlertDialog(
        val alertDialogType: Int = 0
    ): DetailsUiEvents()

    object LikeOrDislike: DetailsUiEvents()
    object BookmarkOrUnBookmark: DetailsUiEvents()


}




















