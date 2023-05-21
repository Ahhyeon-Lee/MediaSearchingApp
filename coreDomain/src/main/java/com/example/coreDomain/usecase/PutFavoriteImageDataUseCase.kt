package com.example.coreDomain.usecase

import com.example.coreDomain.data.SearchListData
import com.example.coreDomain.repository.FavoriteRepository
import javax.inject.Inject

class PutFavoriteImageDataUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend fun invoke(data: SearchListData.ImageDocumentData) {
        favoriteRepository.putFavoriteImageData(data)
    }
}