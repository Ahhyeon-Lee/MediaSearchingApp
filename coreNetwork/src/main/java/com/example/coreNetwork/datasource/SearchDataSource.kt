package com.example.coreNetwork.datasource

import com.example.coreNetwork.data.ImageSearchResponseData
import com.example.coreNetwork.data.VideoSearchResponseData

interface SearchDataSource {
    suspend fun getImageSearchResult(query: String, page: Int): ImageSearchResponseData
    suspend fun getVideoSearchResult(query: String, page: Int): VideoSearchResponseData
}