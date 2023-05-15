package com.example.coreDomain.usecase

import com.example.commonModelUtil.data.SearchListData
import com.example.coredatabase.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteImageListUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke() = favoriteRepository.getFavoriteImageListOnce().map {
        SearchListData.ImageDocumentData(
            collection = it.collection,
            thumbnail = it.thumbnail,
            imageUrl = it.imageUrl,
            width = it.width,
            height = it.height,
            datetime = it.datetime,
            isFavorite = true
        )
    }

}