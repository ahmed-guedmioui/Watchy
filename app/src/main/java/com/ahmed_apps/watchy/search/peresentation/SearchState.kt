package com.ahmed_apps.watchy.search.peresentation

import com.ahmed_apps.watchy.main.domain.model.Media

/**
 * @author Ahmed Guedmioui
 */
data class SearchState (
    val isLoading: Boolean = false,
    val searchPage: Int = 1,
    val searchQuery: String = "",
    val searchList: List<Media> = emptyList()
)





















