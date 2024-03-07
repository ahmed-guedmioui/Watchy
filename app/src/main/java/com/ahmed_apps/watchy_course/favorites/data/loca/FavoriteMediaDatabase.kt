package com.ahmed_apps.watchy_course.favorites.data.loca

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author Ahmed Guedmioui
 */
@Database(
    entities = [FavoriteMediaEntity::class],
    version = 1
)
abstract class FavoriteMediaDatabase: RoomDatabase() {
    abstract val favoriteMediaDao: FavoriteMediaDao
}


















