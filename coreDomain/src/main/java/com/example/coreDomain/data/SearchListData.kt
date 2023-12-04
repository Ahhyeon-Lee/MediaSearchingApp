package com.example.coreDomain.data

import com.example.coreDomain.extension.convertDateString
import com.example.coreDomain.extension.getDate
import com.example.coreDomain.extension.toTimeFormat

sealed class SearchListData {

    @Transient
    open val type: String = ""

    @Transient
    open val datetime: String = ""

    @Transient
    open val isFavorite: Boolean = false

    @Transient
    open val thumbnail: String = ""

    fun getConvertedDate() = datetime.convertDateString()
    fun getDate() = datetime.getDate()

    data class PageData(
        val page: Int
    ) : SearchListData()

    data class ImageDocumentData(
        val collection: String,
        override val thumbnail: String,
        val imageUrl: String,
        val width: Int,
        val height: Int,
        override val datetime: String,
        override val isFavorite: Boolean = false,
        override val type: String = "image"
    ) : SearchListData()

    data class VideoDocumentData(
        val title: String,
        override val thumbnail: String,
        val videoUrl: String,
        val playTime: Int,
        override val datetime: String,
        override val isFavorite: Boolean = false,
        override val type: String = "video"
    ) : SearchListData() {
        fun getVideoTime() = playTime.toTimeFormat()
    }
}