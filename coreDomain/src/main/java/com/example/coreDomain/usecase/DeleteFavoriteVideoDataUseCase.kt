package com.example.coreDomain.usecase

import com.example.commonModelUtil.data.SearchListData
import com.example.coredatabase.entity.VideoDocumentEntity
import com.example.coredatabase.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteVideoDataUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun invoke(data: SearchListData.VideoDocumentData) {
        favoriteRepository.deleteFavoriteVideoData(
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