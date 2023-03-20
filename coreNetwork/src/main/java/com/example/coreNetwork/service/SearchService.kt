package com.example.coreNetwork.service

import com.example.commonModelUtil.search.ImageSearchResponseData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("image")
    fun getImageSearchResult(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "recency",
        @Query("size") size: Int = 80,
    ): Flow<ImageSearchResponseData>
}