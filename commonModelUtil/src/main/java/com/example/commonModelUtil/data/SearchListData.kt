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

    @Transient
    open val thumbnail: String = ""

    fun getConvertedDate() = datetime.convertDateString()
    fun getDate() = datetime.getDate()

    data class ImageDocumentData(
        val collection: String,
        override val thumbnail: String,
        val imageUrl: String,
        val width: Int,
        val height: Int,
        override val datetime: String,
        override var isFavorite: Boolean,
        override val type: String = "image"
    ) : SearchListData()

    data class VideoDocumentData(
        val title: String,
        override val thumbnail: String,
        val videoUrl: String,
        val playTime: Int,
        override val datetime: String,
        override var isFavorite: Boolean,
        override val type: String = "video"
    ) : SearchListData() {
        fun getVideoTime() = playTime.toTimeFormat()
    }
}