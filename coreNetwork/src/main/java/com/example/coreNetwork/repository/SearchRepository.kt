package com.example.coreNetwork.repository

interface SearchRepository {

    fun getImageSearchResult(query: String, page: Int)
}