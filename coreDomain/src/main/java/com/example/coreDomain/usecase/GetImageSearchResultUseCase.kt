package com.example.coreDomain.usecase

import com.example.coreDomain.data.ImageSearchResultData
import com.example.coreDomain.data.SearchListData
import com.example.coreDomain.repository.SearchRepository
import javax.inject.Inject

class GetImageSearchResultUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend fun invoke(
        query: String,
        page: Int,
        favoriteList: List<SearchListData.ImageDocumentData> = listOf()
    ): ImageSearchResultData = repository.getImageSearchResult(query, page).apply {
        list.map { data ->
            data.apply {
                isFavorite = data.thumbnail in favoriteList.map { it.thumbnail }
            }
        }
    }
}

