package com.example.coredatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coredatabase.dao.FavoriteDao
import com.example.coredatabase.entity.ImageDocumentEntity
import com.example.coredatabase.entity.VideoDocumentEntity

@Database(
    entities = [ImageDocumentEntity::class, VideoDocumentEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDao
}