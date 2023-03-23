package com.example.coreNetwork.datasource

import com.example.commonModelUtil.data.ImageSearchResponseData
import com.example.commonModelUtil.data.VideoSearchResponseData

interface SearchDataSource {
    suspend fun getImageSearchResult(query: String, page: Int): ImageSearchResponseData
    suspend fun getVideoSearchResult(query: String, page: Int): VideoSearchResponseData
}