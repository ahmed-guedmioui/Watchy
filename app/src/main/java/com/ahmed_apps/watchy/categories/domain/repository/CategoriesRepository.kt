package com.ahmed_apps.watchy.categories.domain.repository

import com.ahmed_apps.watchy.main.domain.model.Media

/**
 * @author Ahmed Guedmioui
 */
interface CategoriesRepository {

    suspend fun getActionAndAdventure() : List<Media>
    suspend fun getDrama(): List<Media>
    suspend fun getComedy(): List<Media>
    suspend fun getSciFiAndFantasy(): List<Media>
    suspend fun getAnimation(): List<Media>

}


















