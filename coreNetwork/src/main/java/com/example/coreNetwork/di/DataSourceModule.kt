package com.example.coreNetwork.di

import com.example.coreNetwork.datasource.SearchDataSource
import com.example.coreNetwork.datasource.SearchDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindsSearchDataSource(searchDataSource: SearchDataSourceImpl): SearchDataSource

}