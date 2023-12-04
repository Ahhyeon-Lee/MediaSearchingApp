package com.example.coreDomain.usecase

import com.example.coreDomain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteListUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    fun invoke() = favoriteRepository.getFavoriteList()
}