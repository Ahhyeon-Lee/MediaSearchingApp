package com.android.corepreference.di

import com.android.corepreference.repository.FavoriteRepositoryImpl
import com.example.coreDomain.repository.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsFavoriteRepository(searchRepository: FavoriteRepositoryImpl): FavoriteRepository
}