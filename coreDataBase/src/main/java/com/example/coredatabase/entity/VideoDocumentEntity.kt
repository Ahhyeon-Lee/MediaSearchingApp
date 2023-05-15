package com.example.coredatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoDocumentEntity(
    @PrimaryKey
    @ColumnInfo(name = "favoriteTime") val favoriteTime: Long = 0,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "videoUrl") val videoUrl: String,
    @ColumnInfo(name = "playTime") val playTime: Int,
    @ColumnInfo(name = "datetime") val datetime: String,
)