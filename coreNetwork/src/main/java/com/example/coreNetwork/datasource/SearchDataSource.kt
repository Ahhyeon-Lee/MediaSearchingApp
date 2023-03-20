package com.example.coreNetwork.datasource

import com.example.commonModelUtil.search.ImageSearchResponseData
import kotlinx.coroutines.flow.Flow

interface SearchDataSource {

    fun getImageSearchResult(query: String, page: Int): Flow<ImageSearchResponseData>
}