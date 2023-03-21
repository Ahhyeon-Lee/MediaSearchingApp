package com.example.coreNetwork.datasource

import com.example.commonModelUtil.search.ImageSearchResponseData
import com.example.coreNetwork.service.SearchService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val service: SearchService
) : SearchDataSource {

    override fun getImageSearchResult(query: String, page: Int) =
        service.getImageSearchResult(query, page)

    override fun getVideoSearchResult(query: String, page: Int) =
        service.getVideoSearchResult(query, page)

    override suspend fun getImageSearchResult2(query: String, page: Int) =
        service.getImageSearchResult2(query, page)

    override suspend fun getVideoSearchResult2(query: String, page: Int) =
        service.getVideoSearchResult2(query, page)
}