package com.example.coreDomain.usecase

import com.example.commonModelUtil.data.SearchListData
import com.example.coredatabase.entity.ImageDocumentEntity
import com.example.coredatabase.entity.VideoDocumentEntity
import com.example.coredatabase.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteImageDataUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun invoke(data: SearchListData.ImageDocumentData) {
        favoriteRepository.deleteFavoriteImageData(
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
}