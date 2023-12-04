package com.android.corepreference.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.android.corepreference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        EncryptedSharedPreferences.create(
            "android",
            "media_searching",
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    @Singleton
    @Provides
    fun providePreferenceUtil(sharedPreferences: SharedPreferences): PreferenceManager =
        PreferenceManager(sharedPreferences)
}