package com.example.coreNetwork.service

import com.example.coreNetwork.data.ImageSearchResponseData
import com.example.coreNetwork.data.VideoSearchResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("image")
    suspend fun getImageSearchResult(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "recency",
        @Query("size") size: Int = 30,
    ): ImageSearchResponseData

    @GET("vclip")
    suspend fun getVideoSearchResult(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "recency",
        @Query("size") size: Int = 30,
    ): VideoSearchResponseData
}