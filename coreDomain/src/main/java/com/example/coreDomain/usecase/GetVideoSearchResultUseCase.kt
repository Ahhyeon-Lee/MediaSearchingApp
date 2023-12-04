package com.example.coreDomain.usecase

import com.example.coreDomain.data.VideoSearchResultData
import javax.inject.Inject

class GetVideoSearchResultUseCase @Inject constructor(
    private val getSearchVideoListUseCase: GetSearchVideoListUseCase,
    private val getFavoriteVideoListUseCase: GerFavoriteVideoListUseCase
) {
    suspend fun invoke(query: String, page: Int): VideoSearchResultData {
        val favoriteList = getFavoriteVideoListUseCase.invoke().map { it.thumbnail }
        return with(getSearchVideoListUseCase.invoke(query, page)) {
            VideoSearchResultData(
                isEnd,
                list.map { data ->
                    data.copy(isFavorite = data.thumbnail in favoriteList)
                }
            )
        }
    }
}