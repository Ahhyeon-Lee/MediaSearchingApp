package com.example.coreDomain.usecase

import com.example.commonModelUtil.ResultState
import com.example.commonModelUtil.extension.resultState
import com.example.commonModelUtil.search.ImageSearchResponseData
import com.example.coreNetwork.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageSearchResultUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    fun invoke(query: String, page: Int): Flow<ResultState<ImageSearchResponseData>> =
        repository.getImageSearchResult(query, page).resultState()

}