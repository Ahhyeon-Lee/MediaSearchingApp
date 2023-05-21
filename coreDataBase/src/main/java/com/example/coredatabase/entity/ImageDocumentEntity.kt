package com.example.coredatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageDocumentEntity(
    @PrimaryKey
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "favoriteTime") val favoriteTime: Long = 0,
    @ColumnInfo(name = "collection") val collection: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "datetime") val datetime: String
)