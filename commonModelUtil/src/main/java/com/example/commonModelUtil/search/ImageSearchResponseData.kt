package com.example.commonModelUtil.search

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
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("image_url") val imageUrl: String,
    val width: Int,
    val height: Int,
    val datetime: String,
) {
    fun getTime() {

    }
}