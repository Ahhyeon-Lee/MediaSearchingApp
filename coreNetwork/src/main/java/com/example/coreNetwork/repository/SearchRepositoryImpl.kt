package com.example.coreNetwork.repository

import com.example.coreNetwork.datasource.SearchDataSource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: SearchDataSource
): SearchRepository {

    override fun getImageSearchResult(query: String, page: Int) =
        dataSource.getImageSearchResult(query, page)

}