package com.example.coredatabase.datasource

import com.example.coredatabase.entity.ImageDocumentEntity
import com.example.coredatabase.entity.VideoDocumentEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteLocalDataSource {
    suspend fun getFavoriteImageListOnce(): List<ImageDocumentEntity>
    suspend fun getFavoriteVideoListOnce(): List<VideoDocumentEntity>

    fun observeFavoriteImageList(): Flow<List<ImageDocumentEntity>>
    fun observeFavoriteVideoList(): Flow<List<VideoDocumentEntity>>

    suspend fun putFavoriteImageData(data: ImageDocumentEntity)
    suspend fun putFavoriteVideoData(data: VideoDocumentEntity)

    suspend fun deleteFavoriteImageData(data: ImageDocumentEntity)
    suspend fun deleteFavoriteVideoData(data: VideoDocumentEntity)
}