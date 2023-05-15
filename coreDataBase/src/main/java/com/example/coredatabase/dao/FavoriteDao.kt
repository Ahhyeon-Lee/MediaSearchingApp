package com.example.coredatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.coredatabase.entity.ImageDocumentEntity
import com.example.coredatabase.entity.VideoDocumentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM ImageDocumentEntity")
    suspend fun getFavoriteImageListOnce(): List<ImageDocumentEntity>

    @Query("SELECT * FROM VideoDocumentEntity")
    suspend fun getFavoriteVideoListOnce(): List<VideoDocumentEntity>

    @Query("SELECT * FROM ImageDocumentEntity")
    fun observeFavoriteImageList(): Flow<List<ImageDocumentEntity>>

    @Query("SELECT * FROM VideoDocumentEntity")
    fun observeFavoriteVideoList(): Flow<List<VideoDocumentEntity>>

    @Insert
    suspend fun putFavoriteImageData(data: ImageDocumentEntity)

    @Insert
    suspend fun putFavoriteVideoData(data: VideoDocumentEntity)
}