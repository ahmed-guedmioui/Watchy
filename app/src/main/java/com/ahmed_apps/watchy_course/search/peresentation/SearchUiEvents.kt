package com.ahmed_apps.watchy_course.search.peresentation

import com.ahmed_apps.watchy_course.main.domain.model.Media

/**
 * @author Ahmed Guedmioui
 */
sealed class SearchUiEvents {
    data class OnSearchQueryChange(
        val newQuery: String
    ): SearchUiEvents()

    data class OnSearchItemClick(
        val media: Media
    ): SearchUiEvents()

    object OnPaginate: SearchUiEvents()
}






















