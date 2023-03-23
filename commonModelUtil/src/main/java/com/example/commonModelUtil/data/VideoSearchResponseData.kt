package com.example.commonModelUtil.data

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