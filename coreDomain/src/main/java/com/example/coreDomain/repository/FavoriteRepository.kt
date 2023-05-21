package com.example.coreDomain.repository

import com.example.coreDomain.data.SearchListData
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun getFavoriteImageListOnce(): List<SearchListData.ImageDocumentData>
    suspend fun getFavoriteVideoListOnce(): List<SearchListData.VideoDocumentData>

    fun observeFavoriteImageList(): Flow<List<SearchListData.ImageDocumentData>>
    fun observeFavoriteVideoList(): Flow<List<SearchListData.VideoDocumentData>>

    suspend fun putFavoriteImageData(data: SearchListData.ImageDocumentData)
    suspend fun putFavoriteVideoData(data: SearchListData.VideoDocumentData)

    suspend fun deleteFavoriteImageData(data: SearchListData.ImageDocumentData)
    suspend fun deleteFavoriteVideoData(data: SearchListData.VideoDocumentData)
}