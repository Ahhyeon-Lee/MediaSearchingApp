package com.example.coreDomain.usecase

import com.example.commonModelUtil.data.SearchListData
import com.example.coreNetwork.repository.SearchRepository
import javax.inject.Inject

class GetImageSearchResultUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend fun invoke(
        query: String,
        page: Int,
        favoriteList: List<SearchListData.ImageDocumentData> = listOf()
    ): Pair<Boolean, List<SearchListData.ImageDocumentData>> =
        with(repository.getImageSearchResult(query, page)) {
            Pair(
                this.meta.isEnd,
                this.documents.map {
                    SearchListData.ImageDocumentData(
                        collection = it.collection,
                        thumbnail = it.thumbnail_url,
                        imageUrl = it.image_url,
                        width = it.width,
                        height = it.height,
                        datetime = it.datetime,
                        isFavorite = it.thumbnail_url in favoriteList.map { it.thumbnail }
                    )
                }
            )
        }
}

