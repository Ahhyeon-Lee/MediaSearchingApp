package com.example.coreDomain.usecase

import com.example.coreDomain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteImageListFlowUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke() = favoriteRepository.observeFavoriteImageList()

}