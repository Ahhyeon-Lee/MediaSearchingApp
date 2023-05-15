package com.example.coreDomain.usecase

import com.example.commonModelUtil.data.SearchListData
import com.example.coredatabase.repository.FavoriteRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteImageListFlowUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke() = favoriteRepository.observeFavoriteImageList().map { list ->
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