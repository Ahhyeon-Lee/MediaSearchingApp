package com.example.coredatabase.di

import com.example.coredatabase.datasource.FavoriteLocalDataSource
import com.example.coredatabase.datasource.FavoriteLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindsFavoriteLocalDataSource(searchDataSource: FavoriteLocalDataSourceImpl): FavoriteLocalDataSource

}