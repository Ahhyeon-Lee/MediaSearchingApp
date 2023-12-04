package com.example.coreNetwork.di

import com.example.coreDomain.repository.SearchRepository
import com.example.coreNetwork.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository
}