package com.example.coredatabase.repository

import com.example.coredatabase.datasource.FavoriteLocalDataSource
import com.example.coredatabase.entity.ImageDocumentEntity
import com.example.coredatabase.entity.VideoDocumentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDataSource: FavoriteLocalDataSource
) : FavoriteRepository {
    override suspend fun getFavoriteImageListOnce(): List<ImageDocumentEntity> =
        favoriteDataSource.getFavoriteImageListOnce()

    override suspend fun getFavoriteVideoListOnce(): List<VideoDocumentEntity> =
        favoriteDataSource.getFavoriteVideoListOnce()

    override fun observeFavoriteImageList(): Flow<List<ImageDocumentEntity>> =
        favoriteDataSource.observeFavoriteImageList()

    override fun observeFavoriteVideoList(): Flow<List<VideoDocumentEntity>> =
        favoriteDataSource.observeFavoriteVideoList()

    override suspend fun putFavoriteImageData(data: ImageDocumentEntity) {
        favoriteDataSource.putFavoriteImageData(data)
    }

    override suspend fun putFavoriteVideoData(data: VideoDocumentEntity) {
        favoriteDataSource.putFavoriteVideoData(data)
    }
}