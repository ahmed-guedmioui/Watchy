package com.ahmed_apps.watchy_course.favorites.di

import android.app.Application
import androidx.room.Room
import com.ahmed_apps.watchy_course.favorites.data.loca.FavoriteMediaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Ahmed Guedmioui
 */
@Module
@InstallIn(SingletonComponent::class)
object FavoritesModule {

    @Provides
    @Singleton
    fun providesMediaDatabase(application: Application): FavoriteMediaDatabase {
        return Room.databaseBuilder(
            application,
            FavoriteMediaDatabase::class.java,
            "favorites-db.db"
        ).build()
    }


}

















