package com.ahmed_apps.watchy.favorites.presentation

import com.ahmed_apps.watchy.main.domain.model.Media

/**
 * @author Ahmed Guedmioui
 */
data class FavoritesState (
    val likedList: List<Media> = emptyList(),
    val bookmarksList: List<Media> = emptyList()
)