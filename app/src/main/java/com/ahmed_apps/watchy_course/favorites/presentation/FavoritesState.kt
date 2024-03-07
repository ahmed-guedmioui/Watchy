package com.ahmed_apps.watchy_course.favorites.presentation

import com.ahmed_apps.watchy_course.main.domain.model.Media

/**
 * @author Ahmed Guedmioui
 */
data class FavoritesState (
    val likedList: List<Media> = emptyList(),
    val bookmarksList: List<Media> = emptyList()
)