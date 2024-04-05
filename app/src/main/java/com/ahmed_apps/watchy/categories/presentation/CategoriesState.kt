package com.ahmed_apps.watchy.categories.presentation

import com.ahmed_apps.watchy.main.domain.model.Media

/**
 * @author Ahmed Guedmioui
 */
data class CategoriesState(

    val actionAndAdventureList: List<Media> = emptyList(),
    val dramaList: List<Media> = emptyList(),
    val comedyList: List<Media> = emptyList(),
    val sciFiAndFantasyList: List<Media> = emptyList(),
    val animationList: List<Media> = emptyList()
)















