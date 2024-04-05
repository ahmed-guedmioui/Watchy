package com.ahmed_apps.watchy.search.peresentation

import com.ahmed_apps.watchy.main.domain.model.Media

/**
 * @author Ahmed Guedmioui
 */
sealed class SearchUiEvents {
    data class OnSearchQueryChange (
        val searchQuery: String
    ): SearchUiEvents()

    data class OnSearchItemClick(
        val media: Media
    ): SearchUiEvents()

    object OnPaginate: SearchUiEvents()
}






















