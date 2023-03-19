package com.example.commonModelUtil

data class ImageSearchResponseData(
    val meta: MetaData,
    val documents: List<DocumentData>
)

data class MetaData(
    val is_end: Boolean
)

data class DocumentData(
    val collection: String,
    val thumbnail_url: String,
    val image_url: String,
    val datetime: String,
)