package com.example.coreDomain.usecase

import com.example.commonModelUtil.search.SearchListData
import com.example.coreNetwork.repository.SearchRepository
import javax.inject.Inject

class GetVideoSearchResultUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend fun invoke(
        query: String,
        page: Int
    ): Pair<Boolean, List<SearchListData.VideoDocumentData>> =
        with(repository.getVideoSearchResult(query, page)) {
            Pair(
                this.meta.isEnd,
                this.documents.map {
                    SearchListData.VideoDocumentData(
                        it.title,
                        it.thumbnail,
                        it.url,
                        it.play_time,
                        it.datetime,
                        false
                    )
                }
            )
        }
}