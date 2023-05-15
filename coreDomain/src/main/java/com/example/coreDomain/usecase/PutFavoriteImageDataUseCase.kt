package com.example.coreDomain.usecase

import com.example.commonModelUtil.data.SearchListData
import com.example.coredatabase.entity.ImageDocumentEntity
import com.example.coredatabase.repository.FavoriteRepository
import javax.inject.Inject

class PutFavoriteImageDataUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun invoke(data: SearchListData.ImageDocumentData) {
        favoriteRepository.putFavoriteImageData(
            ImageDocumentEntity(
                collection = data.collection,
                thumbnail = data.thumbnail,
                imageUrl = data.imageUrl,
                width = data.width,
                height = data.height,
                datetime = data.datetime,
                favoriteTime = System.currentTimeMillis()
            )
        )
    }
}