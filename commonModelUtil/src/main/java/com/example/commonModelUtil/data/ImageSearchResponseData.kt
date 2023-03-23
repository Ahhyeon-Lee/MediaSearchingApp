package com.example.commonModelUtil.data

import com.google.gson.annotations.SerializedName

data class ImageSearchResponseData(
    val meta: MetaData,
    val documents: List<ImageDocumentData>
)

data class MetaData(
    @SerializedName("is_end") val isEnd: Boolean
)

data class ImageDocumentData(
    val collection: String,
    val thumbnail_url: String,
    val image_url: String,
    val width: Int,
    val height: Int,
    val datetime: String,
)