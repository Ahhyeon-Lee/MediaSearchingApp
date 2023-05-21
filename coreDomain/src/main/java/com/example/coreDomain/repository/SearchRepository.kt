package com.example.coreDomain.repository

import com.example.coreDomain.data.ImageSearchResultData
import com.example.coreDomain.data.VideoSearchResultData

interface SearchRepository {
    suspend fun getImageSearchResult(query: String, page: Int): ImageSearchResultData
    suspend fun getVideoSearchResult(query: String, page: Int): VideoSearchResultData
}