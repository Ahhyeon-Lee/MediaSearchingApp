package com.example.coreDomain.usecase

import com.example.commonModelUtil.data.SearchListData
import com.example.coreNetwork.repository.SearchRepository
import com.example.coreNetwork.repository.SearchRepositoryImpl_Factory
import javax.inject.Inject

class GetVideoSearchResultUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend fun invoke(
        query: String,
        page: Int,
        favoriteList: List<SearchListData.VideoDocumentData>
    ): Pair<Boolean, List<SearchListData.VideoDocumentData>> =
        with(repository.getVideoSearchResult(query, page)) {
            Pair(
                this.meta.isEnd,
                this.documents.map {
                    SearchListData.VideoDocumentData(
                        title = it.title,
                        thumbnail = it.thumbnail,
                        videoUrl = it.url,
                        playTime = it.play_time,
                        datetime = it.datetime,
                        isFavorite = it.thumbnail in favoriteList.map { it.thumbnail }
                    )
                }
            )
        }
}