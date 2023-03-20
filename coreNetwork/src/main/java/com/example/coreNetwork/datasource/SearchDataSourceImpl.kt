package com.example.coreNetwork.datasource

import com.example.commonModelUtil.search.ImageSearchResponseData
import com.example.coreNetwork.service.SearchService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val service: SearchService
) : SearchDataSource {

    override fun getImageSearchResult(query: String, page: Int): Flow<ImageSearchResponseData> =
        service.getImageSearchResult(query, page)

}