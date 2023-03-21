package com.example.coreNetwork.repository

import com.example.commonModelUtil.search.ImageSearchResponseData
import com.example.commonModelUtil.search.VideoSearchResponseData
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getImageSearchResult(query: String, page: Int): Flow<ImageSearchResponseData>
    fun getVideoSearchResult(query: String, page: Int): Flow<VideoSearchResponseData>

    suspend fun getImageSearchResult2(query: String, page: Int): ImageSearchResponseData
    suspend fun getVideoSearchResult2(query: String, page: Int): VideoSearchResponseData
}