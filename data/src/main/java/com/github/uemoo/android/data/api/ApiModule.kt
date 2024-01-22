package com.github.uemoo.android.data.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {
    @Provides
    fun provideCatApi(retrofit: Retrofit): CatApi = retrofit.create(CatApi::class.java)
}
