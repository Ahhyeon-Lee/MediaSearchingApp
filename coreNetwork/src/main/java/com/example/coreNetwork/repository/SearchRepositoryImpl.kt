package com.example.coreNetwork.repository

import com.example.coreNetwork.datasource.SearchDataSource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: SearchDataSource
): SearchRepository {

    override fun getImageSearchResult(query: String, page: Int) =
        dataSource.getImageSearchResult(query, page)

    override fun getVideoSearchResult(query: String, page: Int) =
        dataSource.getVideoSearchResult(query, page)

    override suspend  fun getImageSearchResult2(query: String, page: Int) =
        dataSource.getImageSearchResult2(query, page)

    override suspend  fun getVideoSearchResult2(query: String, page: Int) =
        dataSource.getVideoSearchResult2(query, page)
}