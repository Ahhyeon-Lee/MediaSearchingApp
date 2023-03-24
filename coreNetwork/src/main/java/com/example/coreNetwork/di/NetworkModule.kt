package com.example.coreNetwork.di

import com.example.coreNetwork.LoggingInterceptor
import com.example.coreNetwork.adapter.FlowCallAdapterFactory
import com.example.coreNetwork.service.SearchService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    val apiKey = "568d8bac516d6d541580d907ce5ad4fc"

    @Singleton
    @Provides
    fun getHeaderInterceptor() = Interceptor { chain ->
        val builder = chain.request().newBuilder().apply {
            addHeader("Content-Type", "application/json;charset=UTF-8")
            addHeader("Authorization", "KakaoAK $apiKey")
        }
        chain.proceed(builder.build())
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val timeout = 2L
        val timeUnit = TimeUnit.MINUTES

        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .addInterceptor(getHeaderInterceptor())
            .connectTimeout(timeout, timeUnit)
            .writeTimeout(timeout, timeUnit)
            .readTimeout(timeout, timeUnit)
            .retryOnConnectionFailure(true)

        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(
        okHttpClient: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))

    @Singleton
    @Provides
    fun providesSearchService(
        retrofit: Retrofit.Builder
    ): SearchService =
        retrofit.baseUrl("https://dapi.kakao.com/v2/search/").build().create(SearchService::class.java)
}