package com.example.coredatabase.di

import com.example.coredatabase.repository.FavoriteRepository
import com.example.coredatabase.repository.FavoriteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalRepositoryModule {
    @Binds
    internal abstract fun bindsFavoriteRepository(searchRepository: FavoriteRepositoryImpl): FavoriteRepository
}