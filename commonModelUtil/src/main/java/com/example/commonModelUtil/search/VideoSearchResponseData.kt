package com.example.commonModelUtil.search

import com.example.commonModelUtil.extension.convertDateString
import com.google.gson.annotations.SerializedName

data class VideoSearchResponseData(
    val meta: MetaData,
    val documents: List<VideoDocumentData>
)

data class VideoDocumentData(
    val title: String,
    val thumbnail: String,
    val url: String,
    val play_time: Int,
    val datetime: String,
)