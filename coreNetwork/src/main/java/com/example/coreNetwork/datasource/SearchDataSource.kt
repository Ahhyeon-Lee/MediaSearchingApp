package com.example.coreNetwork.datasource

import com.example.commonModelUtil.search.ImageSearchResponseData
import com.example.commonModelUtil.search.VideoSearchResponseData

interface SearchDataSource {
    suspend fun getImageSearchResult(query: String, page: Int): ImageSearchResponseData
    suspend fun getVideoSearchResult(query: String, page: Int): VideoSearchResponseData
}