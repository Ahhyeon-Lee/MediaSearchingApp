package com.example.coreDomain.usecase

import com.example.coreDomain.data.SearchListData
import com.example.coreDomain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteVideoListUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke() = favoriteRepository.getFavoriteVideoListOnce().map {
        SearchListData.VideoDocumentData(
            title = it.title,
            thumbnail = it.thumbnail,
            videoUrl = it.videoUrl,
            playTime = it.playTime,
            datetime = it.datetime,
            isFavorite = true
        )
    }

}