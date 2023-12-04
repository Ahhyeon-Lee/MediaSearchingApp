package com.example.coreDomain.usecase

import com.example.coreDomain.data.SearchListData
import com.example.coreDomain.repository.FavoriteRepository
import javax.inject.Inject

class PutFavoriteListUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    fun invoke(data: SearchListData) = favoriteRepository.putFavoriteData(data)
}