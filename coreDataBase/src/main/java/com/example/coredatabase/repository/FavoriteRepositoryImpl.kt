package com.example.coredatabase.repository

import com.example.coreDomain.data.SearchListData
import com.example.coreDomain.repository.FavoriteRepository
import com.example.coredatabase.datasource.FavoriteLocalDataSource
import com.example.coredatabase.entity.ImageDocumentEntity
import com.example.coredatabase.entity.VideoDocumentEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDataSource: FavoriteLocalDataSource
) : FavoriteRepository {
    override suspend fun getFavoriteImageListOnce(): List<SearchListData.ImageDocumentData> {
        return favoriteDataSource.getFavoriteImageListOnce().map {
            SearchListData.ImageDocumentData(
                collection = it.collection,
                thumbnail = it.thumbnail,
                imageUrl = it.imageUrl,
                width = it.width,
                height = it.height,
                datetime = it.datetime,
                isFavorite = true,
                favoriteTime = it.favoriteTime
            )
        }
    }

    override suspend fun getFavoriteVideoListOnce(): List<SearchListData.VideoDocumentData> {
        return favoriteDataSource.getFavoriteVideoListOnce().map {
            SearchListData.VideoDocumentData(
                title = it.title,
                thumbnail = it.thumbnail,
                videoUrl = it.videoUrl,
                playTime = it.playTime,
                datetime = it.datetime,
                isFavorite = true,
                favoriteTime = it.favoriteTime
            )
        }
    }

    override fun observeFavoriteImageList(): Flow<List<SearchListData.ImageDocumentData>> {
        return favoriteDataSource.observeFavoriteImageList().map { list ->
            list.map {
                SearchListData.ImageDocumentData(
                    collection = it.collection,
                    thumbnail = it.thumbnail,
                    imageUrl = it.imageUrl,
                    width = it.width,
                    height = it.height,
                    datetime = it.datetime,
                    isFavorite = true,
                    favoriteTime = it.favoriteTime
                )
            }
        }
    }

    override fun observeFavoriteVideoList(): Flow<List<SearchListData.VideoDocumentData>> {
        return favoriteDataSource.observeFavoriteVideoList().map { list ->
            list.map {
                SearchListData.VideoDocumentData(
                    title = it.title,
                    thumbnail = it.thumbnail,
                    videoUrl = it.videoUrl,
                    playTime = it.playTime,
                    datetime = it.datetime,
                    isFavorite = true,
                    favoriteTime = it.favoriteTime
                )
            }
        }
    }

    override suspend fun putFavoriteImageData(data: SearchListData.ImageDocumentData) {
        favoriteDataSource.putFavoriteImageData(
            ImageDocumentEntity(
                thumbnail = data.thumbnail,
                collection = data.collection,
                imageUrl = data.imageUrl,
                width = data.width,
                height = data.height,
                datetime = data.datetime,
                favoriteTime = System.currentTimeMillis()
            )
        )
    }

    override suspend fun putFavoriteVideoData(data: SearchListData.VideoDocumentData) {
        favoriteDataSource.putFavoriteVideoData(
            VideoDocumentEntity(
                title = data.title,
                thumbnail = data.thumbnail,
                videoUrl = data.videoUrl,
                playTime = data.playTime,
                datetime = data.datetime,
                favoriteTime = System.currentTimeMillis()
            )
        )
    }

    override suspend fun deleteFavoriteImageData(data: SearchListData.ImageDocumentData) {
        favoriteDataSource.deleteFavoriteImageData(
            ImageDocumentEntity(
                thumbnail = data.thumbnail,
                collection = data.collection,
                imageUrl = data.imageUrl,
                width = data.width,
                height = data.height,
                datetime = data.datetime,
                favoriteTime = data.favoriteTime
            )
        )
    }

    override suspend fun deleteFavoriteVideoData(data: SearchListData.VideoDocumentData) {
        favoriteDataSource.deleteFavoriteVideoData(
            VideoDocumentEntity(
                thumbnail = data.thumbnail,
                title = data.title,
                videoUrl = data.videoUrl,
                playTime = data.playTime,
                datetime = data.datetime,
                favoriteTime = data.favoriteTime
            )
        )
    }
}