package com.example.coreNetwork.repository

import com.example.coreDomain.data.ImageSearchResultData
import com.example.coreDomain.data.SearchListData
import com.example.coreDomain.data.VideoSearchResultData
import com.example.coreDomain.repository.SearchRepository
import com.example.coreNetwork.datasource.SearchDataSource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: SearchDataSource
) : SearchRepository {
    override suspend fun getImageSearchResult(query: String, page: Int) =
        with(dataSource.getImageSearchResult(query, page)) {
            ImageSearchResultData(
                this.meta.isEnd,
                this.documents.map {
                    SearchListData.ImageDocumentData(
                        collection = it.collection,
                        thumbnail = it.thumbnail_url,
                        imageUrl = it.image_url,
                        width = it.width,
                        height = it.height,
                        datetime = it.datetime
                    )
                }
            )
        }

    override suspend fun getVideoSearchResult(query: String, page: Int) =
        with(dataSource.getVideoSearchResult(query, page)) {
            VideoSearchResultData(
                this.meta.isEnd,
                this.documents.map {
                    SearchListData.VideoDocumentData(
                        title = it.title,
                        thumbnail = it.thumbnail,
                        videoUrl = it.url,
                        playTime = it.play_time,
                        datetime = it.datetime
                    )
                }
            )
        }
}