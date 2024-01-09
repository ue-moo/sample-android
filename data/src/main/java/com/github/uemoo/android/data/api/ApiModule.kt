package com.github.uemoo.android.data.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {
    @Provides
    fun provideSampleApi(): SampleApi = SampleApi()
}
