package com.example.coreNetwork.repository

import com.example.commonModelUtil.search.ImageSearchResponseData
import com.example.commonModelUtil.search.VideoSearchResponseData

interface SearchRepository {
    suspend fun getImageSearchResult(query: String, page: Int): ImageSearchResponseData
    suspend fun getVideoSearchResult(query: String, page: Int): VideoSearchResponseData
}