package com.example.coreDomain.usecase

import com.example.coreDomain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteVideoListOnceUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke() = favoriteRepository.getFavoriteVideoListOnce()

}