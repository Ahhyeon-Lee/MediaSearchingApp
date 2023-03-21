package com.example.coreNetwork.datasource

import com.example.coreNetwork.service.SearchService
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val service: SearchService
) : SearchDataSource {
    override suspend fun getImageSearchResult(query: String, page: Int) =
        service.getImageSearchResult(query, page)

    override suspend fun getVideoSearchResult(query: String, page: Int) =
        service.getVideoSearchResult(query, page)
}