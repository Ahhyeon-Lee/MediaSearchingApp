package com.example.commonModelUtil.search

import com.google.gson.annotations.SerializedName

data class VideoSearchResponseData(
    val meta: MetaData,
    val documents: List<VideoDocumentData>
)

data class VideoDocumentData(
    val title: String,
    val thumbnail: String,
    val url: String,
    @SerializedName("play_time") val playTime: Int,
    val datetime: String,
)