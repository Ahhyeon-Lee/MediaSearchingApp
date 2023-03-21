package com.example.coreNetwork.service

import com.example.commonModelUtil.search.ImageSearchResponseData
import com.example.commonModelUtil.search.VideoSearchResponseData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("image")
    fun getImageSearchResult(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "recency",
        @Query("size") size: Int = 30,
    ): Flow<ImageSearchResponseData>

    @GET("vclip")
    fun getVideoSearchResult(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "recency",
        @Query("size") size: Int = 30,
    ): Flow<VideoSearchResponseData>

    @GET("image")
    suspend fun getImageSearchResult2(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "recency",
        @Query("size") size: Int = 30,
    ): ImageSearchResponseData

    @GET("vclip")
    suspend fun getVideoSearchResult2(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "recency",
        @Query("size") size: Int = 30,
    ): VideoSearchResponseData
}