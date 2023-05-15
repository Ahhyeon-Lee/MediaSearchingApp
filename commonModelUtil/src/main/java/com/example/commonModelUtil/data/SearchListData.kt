package com.example.commonModelUtil.data

import com.example.commonModelUtil.extension.convertDateString
import com.example.commonModelUtil.extension.getDate
import com.example.commonModelUtil.extension.toTimeFormat

sealed class SearchListData {

    @Transient
    open val type: String = ""

    @Transient
    open val datetime: String = ""

    @Transient
    open var isFavorite: Boolean = false

    open var favoriteTime: Long = 0

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
        override var isFavorite: Boolean,
        override val type: String = "image",
        override var favoriteTime: Long = 0
    ) : SearchListData()

    data class VideoDocumentData(
        val title: String,
        override val thumbnail: String,
        val videoUrl: String,
        val playTime: Int,
        override val datetime: String,
        override var isFavorite: Boolean,
        override val type: String = "video",
        override var favoriteTime: Long = 0
    ) : SearchListData() {
        fun getVideoTime() = playTime.toTimeFormat()
    }
}