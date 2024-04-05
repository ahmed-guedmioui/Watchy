package com.ahmed_apps.watchy.favorites.data.loca

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FavoriteMediaDao {

    @Query("SELECT EXISTS(SELECT 1 FROM favoriteMediaEntity WHERE mediaId = :mediaId)")
    suspend fun doesFavoriteMediaExist(
        mediaId: Int
    ): Boolean


    @Upsert
    suspend fun upsertFavoriteMediaItem(
        favoriteMediaEntity: FavoriteMediaEntity
    )

    @Upsert
    suspend fun upsertFavoriteMediaList(
        favoriteMediaEntityList: List<FavoriteMediaEntity>
    )



    @Query("SELECT * FROM favoriteMediaEntity WHERE mediaId = :mediaId")
    suspend fun getFavoriteMediaItemById(
        mediaId: Int
    ): FavoriteMediaEntity?

    @Query("SELECT * FROM favoriteMediaEntity WHERE isLiked = 1")
    suspend fun getLikedMediaList(): List<FavoriteMediaEntity>

    @Query("SELECT * FROM favoriteMediaEntity WHERE isBookmarked = 1")
    suspend fun getBookmarkedList(): List<FavoriteMediaEntity>

    @Query("SELECT * FROM favoriteMediaEntity")
    suspend fun getAllFavoriteMediaItem(): List<FavoriteMediaEntity>


    @Delete
    suspend fun deleteFavoriteMediaItem(
        favoriteMediaEntity: FavoriteMediaEntity
    )

    @Query("DELETE FROM favoriteMediaEntity")
    suspend fun deleteAllFavoriteMediaItems()

}












