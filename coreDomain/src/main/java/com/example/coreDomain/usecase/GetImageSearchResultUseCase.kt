package com.example.coreDomain.usecase

import com.example.commonModelUtil.ResultState
import com.example.commonModelUtil.extension.resultState
import com.example.commonModelUtil.search.SearchData
import com.example.coreNetwork.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetImageSearchResultUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    fun invoke(query: String, page: Int): Flow<ResultState<List<SearchData.ImageDocumentData>>> {
        return repository.getImageSearchResult(query, page).map {
            it.documents.map {
                SearchData.ImageDocumentData(
                    it.collection,
                    it.thumbnail_url,
                    it.image_url,
                    it.width,
                    it.height,
                    it.datetime
                )
            }
        }.resultState()
    }

    suspend fun invoke2(query: String, page: Int): List<SearchData.ImageDocumentData> {
        return with(repository.getImageSearchResult2(query, page)) {
            this.documents.map {
                SearchData.ImageDocumentData(
                    it.collection,
                    it.thumbnail_url,
                    it.image_url,
                    it.width,
                    it.height,
                    it.datetime
                )
            }
        }
    }
}

