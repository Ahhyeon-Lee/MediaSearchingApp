package com.example.coreDomain.usecase

import com.example.coreDomain.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteListUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    fun invoke(thumbnail: String) = favoriteRepository.deleteFavoriteData(thumbnail)
}