package com.example.coreNetwork.datasource

interface SearchDataSource {

    fun getImageSearchResult(query: String, page: Int)
}