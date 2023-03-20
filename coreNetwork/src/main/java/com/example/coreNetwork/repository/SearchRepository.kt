package com.example.coreNetwork.repository

import com.example.commonModelUtil.search.ImageSearchResponseData
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getImageSearchResult(query: String, page: Int): Flow<ImageSearchResponseData>
}