package com.example.coreDomain.usecase

import com.example.coreDomain.data.ImageSearchResultData
import javax.inject.Inject

class GetImageSearchResultUseCase @Inject constructor(
    private val getSearchImageListUseCase: GetSearchImageListUseCase,
    private val getFavoriteImageListUseCase: GerFavoriteImageListUseCase
) {
    suspend fun invoke(query: String, page: Int): ImageSearchResultData {
        val favoriteList = getFavoriteImageListUseCase.invoke().map { it.thumbnail }
        return with(getSearchImageListUseCase.invoke(query, page)) {
            ImageSearchResultData(
                isEnd,
                list.map { data ->
                    data.copy(isFavorite = data.thumbnail in favoriteList)
                }
            )
        }
    }
}