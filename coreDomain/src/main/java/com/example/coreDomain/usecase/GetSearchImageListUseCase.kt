package com.example.coreDomain.usecase

import com.example.coreDomain.data.ImageSearchResultData
import com.example.coreDomain.repository.SearchRepository
import javax.inject.Inject

class GetSearchImageListUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend fun invoke(query: String, page: Int): ImageSearchResultData =
        repository.getImageSearchResult(query, page)
}

