package com.example.coreDomain.usecase

import com.example.coreDomain.data.SearchListData
import com.example.coreDomain.data.VideoSearchResultData
import com.example.coreDomain.repository.SearchRepository
import javax.inject.Inject

class GetVideoSearchResultUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend fun invoke(
        query: String,
        page: Int,
        favoriteList: List<SearchListData.VideoDocumentData>
    ): VideoSearchResultData = repository.getVideoSearchResult(query, page).apply {
        list.map { data ->
            data.apply {
                isFavorite = data.thumbnail in favoriteList.map { it.thumbnail }
            }
        }
    }
}