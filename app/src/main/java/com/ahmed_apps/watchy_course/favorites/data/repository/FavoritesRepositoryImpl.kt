package com.ahmed_apps.watchy_course.favorites.data.repository

import com.ahmed_apps.watchy_course.favorites.data.loca.FavoriteMediaDatabase
import com.ahmed_apps.watchy_course.favorites.data.mapper.toFavoriteMediaEntity
import com.ahmed_apps.watchy_course.favorites.data.mapper.toMedia
import com.ahmed_apps.watchy_course.favorites.domain.repository.FavoritesRepository
import com.ahmed_apps.watchy_course.main.data.local.MediaDatabase
import com.ahmed_apps.watchy_course.main.domain.model.Media
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

/**
 * @author Ahmed Guedmioui
 */
class FavoritesRepositoryImpl @Inject constructor(
    favoriteMediaDatabase: FavoriteMediaDatabase,
    mediaDatabase: MediaDatabase
): FavoritesRepository {

    private val mediaDao = mediaDatabase.mediaDao
    private val favoriteMediaDao = favoriteMediaDatabase.favoriteMediaDao

    private val _favoritesDbUpdateEventChannel = Channel<Boolean>()
    override suspend fun favoritesDbUpdateEventFlow(): Flow<Boolean> =
        _favoritesDbUpdateEventChannel.receiveAsFlow()

    override suspend fun upsetFavoritesMediaItem(media: Media) {
        favoriteMediaDao.upsertFavoriteMediaItem(
            media.toFavoriteMediaEntity()
        )

        _favoritesDbUpdateEventChannel.send(true)
    }

    override suspend fun deleteFavoritesMediaItem(media: Media) {
        favoriteMediaDao.deleteFavoriteMediaItem(
            media.toFavoriteMediaEntity()
        )

        _favoritesDbUpdateEventChannel.send(true)
    }

    override suspend fun getMediaItemById(mediaId: Int): Media? {
        return favoriteMediaDao.getFavoriteMediaItemById(
            mediaId
        )?.toMedia()
    }

    override suspend fun getLikedMediaList(): List<Media> {
        val likedList = favoriteMediaDao.getLikedMediaList()

        if (likedList.isNotEmpty()) {
            return likedList.map { it.toMedia() }
        }

        return emptyList()
    }

    override suspend fun getBookmarkedMediaList(): List<Media> {
        val bookmarkedList = favoriteMediaDao.getBookmarkedList()

        if (bookmarkedList.isNotEmpty()) {
            return bookmarkedList.map { it.toMedia() }
        }

        return emptyList()
    }

}





















