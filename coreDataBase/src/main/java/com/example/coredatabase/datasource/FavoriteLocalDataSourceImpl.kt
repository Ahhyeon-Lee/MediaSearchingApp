package com.example.coredatabase.datasource

import com.example.coredatabase.dao.FavoriteDao
import com.example.coredatabase.entity.ImageDocumentEntity
import com.example.coredatabase.entity.VideoDocumentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteLocalDataSourceImpl @Inject constructor(
    private val favoriteService: FavoriteDao
) : FavoriteLocalDataSource {
    override suspend fun getFavoriteImageListOnce(): List<ImageDocumentEntity> =
        favoriteService.getFavoriteImageListOnce()

    override suspend fun getFavoriteVideoListOnce(): List<VideoDocumentEntity> =
        favoriteService.getFavoriteVideoListOnce()

    override fun observeFavoriteImageList(): Flow<List<ImageDocumentEntity>> =
        favoriteService.observeFavoriteImageList()

    override fun observeFavoriteVideoList(): Flow<List<VideoDocumentEntity>> =
        favoriteService.observeFavoriteVideoList()

    override suspend fun putFavoriteImageData(data: ImageDocumentEntity) {
        favoriteService.putFavoriteImageData(data)
    }

    override suspend fun putFavoriteVideoData(data: VideoDocumentEntity) {
        favoriteService.putFavoriteVideoData(data)
    }

    override suspend fun deleteFavoriteImageData(data: ImageDocumentEntity) {
        favoriteService.deleteFavoriteImageData(data.thumbnail)
    }

    override suspend fun deleteFavoriteVideoData(data: VideoDocumentEntity) {
        favoriteService.deleteFavoriteVideoData(data.thumbnail)
    }


}