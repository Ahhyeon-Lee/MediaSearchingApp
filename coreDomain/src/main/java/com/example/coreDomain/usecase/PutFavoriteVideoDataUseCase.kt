package com.example.coreDomain.usecase

import com.example.commonModelUtil.data.SearchListData
import com.example.coredatabase.entity.ImageDocumentEntity
import com.example.coredatabase.entity.VideoDocumentEntity
import com.example.coredatabase.repository.FavoriteRepository
import javax.inject.Inject

class PutFavoriteVideoDataUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun invoke(data: SearchListData.VideoDocumentData) {
        favoriteRepository.putFavoriteVideoData(
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
}