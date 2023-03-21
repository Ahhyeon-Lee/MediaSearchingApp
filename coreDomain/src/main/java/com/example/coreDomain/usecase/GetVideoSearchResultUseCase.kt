package com.example.coreDomain.usecase

import com.example.commonModelUtil.ResultState
import com.example.commonModelUtil.extension.resultState
import com.example.commonModelUtil.search.ImageSearchResponseData
import com.example.commonModelUtil.search.SearchData
import com.example.commonModelUtil.search.VideoSearchResponseData
import com.example.coreNetwork.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetVideoSearchResultUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    fun invoke(query: String, page: Int): Flow<ResultState<List<SearchData.VideoDocumentData>>> =
        repository.getVideoSearchResult(query, page).map {
            it.documents.map {
                SearchData.VideoDocumentData(
                    it.title,
                    it.thumbnail,
                    it.url,
                    it.play_time,
                    it.datetime
                )
            }
        }.resultState()

    suspend fun invoke2(query: String, page: Int): List<SearchData.VideoDocumentData> =
        with(repository.getVideoSearchResult2(query, page)) {
            this.documents.map {
                SearchData.VideoDocumentData(
                    it.title,
                    it.thumbnail,
                    it.url,
                    it.play_time,
                    it.datetime
                )
            }
        }

}