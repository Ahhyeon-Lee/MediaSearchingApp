package com.example.coreDomain.usecase

import com.example.commonModelUtil.data.SearchListData
import com.example.coredatabase.repository.FavoriteRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteVideoListFlowUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke() = favoriteRepository.observeFavoriteVideoList().map { list ->
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