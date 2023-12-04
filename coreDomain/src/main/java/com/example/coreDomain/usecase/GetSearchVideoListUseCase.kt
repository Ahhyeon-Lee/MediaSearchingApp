package com.example.coreDomain.usecase

import com.example.coreDomain.data.VideoSearchResultData
import com.example.coreDomain.repository.SearchRepository
import javax.inject.Inject

class GetSearchVideoListUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend fun invoke(query: String, page: Int): VideoSearchResultData =
        repository.getVideoSearchResult(query, page)
}

