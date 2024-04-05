package com.ahmed_apps.watchy.search.domain.repository

import com.ahmed_apps.watchy.main.domain.model.Media
import com.ahmed_apps.watchy.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * @author Ahmed Guedmioui
 */
interface SearchRepository {

    suspend fun getSearchList(
        query: String,
        page: Int
    ): Flow<Resource<List<Media>>>

}















