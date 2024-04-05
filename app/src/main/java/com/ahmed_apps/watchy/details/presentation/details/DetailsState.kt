package com.ahmed_apps.watchy.details.presentation.details

import com.ahmed_apps.watchy.main.domain.model.Media

/**
 * @author Ahmed Guedmioui
 */
data class DetailsState(
    val media: Media? = null,
    val videoId: String = "",
    val readableTime: String = "",

    val videoList: List<String> = emptyList(),
    val similarList: List<Media> = emptyList(),

    val showAlertDialog: Boolean = false,
    val alertDialogType: Int = 0
)
























