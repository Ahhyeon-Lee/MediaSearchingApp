package com.example.commonModelUtil.search

import com.example.commonModelUtil.extension.convertDateString
import com.example.commonModelUtil.extension.getDate

sealed class SearchListData {

    open val datetime: String = ""
    open var isFavorite: Boolean = false

    fun getConvertedDate() = datetime.convertDateString()
    fun getDate() = datetime.getDate()

    data class ImageDocumentData(
        val collection: String,
        val thumbnail: String,
        val imageUrl: String,
        val width: Int,
        val height: Int,
        override val datetime: String,
        override var isFavorite: Boolean
    ) : SearchListData()

    data class VideoDocumentData(
        val title: String,
        val thumbnail: String,
        val videoUrl: String,
        val playTime: Int,
        override val datetime: String,
        override var isFavorite: Boolean
    ) : SearchListData()
}