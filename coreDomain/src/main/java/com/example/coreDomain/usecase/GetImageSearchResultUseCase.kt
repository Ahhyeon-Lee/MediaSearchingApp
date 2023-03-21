package com.example.coreDomain.usecase

import com.example.commonModelUtil.search.SearchListData
import com.example.coreNetwork.repository.SearchRepository
import javax.inject.Inject

class GetImageSearchResultUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend fun invoke(
        query: String,
        page: Int
    ): Pair<Boolean, List<SearchListData.ImageDocumentData>> =
        with(repository.getImageSearchResult(query, page)) {
            Pair(
                this.meta.isEnd,
                this.documents.map {
                    SearchListData.ImageDocumentData(
                        it.collection,
                        it.thumbnail_url,
                        it.image_url,
                        it.width,
                        it.height,
                        it.datetime
                    )
                }
            )
        }
}

