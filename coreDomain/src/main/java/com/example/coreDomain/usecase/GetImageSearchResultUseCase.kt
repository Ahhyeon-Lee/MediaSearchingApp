package com.example.coreDomain.usecase

import com.example.coreNetwork.repository.SearchRepository
import javax.inject.Inject

class GetImageSearchResultUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    fun invoke(query: String, page: Int) {
        repository.getImageSearchResult(query, page)
    }
}