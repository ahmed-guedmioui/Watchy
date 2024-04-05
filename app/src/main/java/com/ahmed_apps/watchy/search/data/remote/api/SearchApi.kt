package com.ahmed_apps.watchy.search.data.remote.api

import com.ahmed_apps.watchy.main.data.remote.api.MediaApi
import com.ahmed_apps.watchy.main.data.remote.dto.MediaListDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Ahmed Guedmioui
 */
interface SearchApi {

    @GET("search/multi")
    suspend fun getSearchList(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = MediaApi.API_KEY
    ): MediaListDto?

}



















