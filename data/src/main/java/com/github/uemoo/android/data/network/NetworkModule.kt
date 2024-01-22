package com.github.uemoo.android.data.network

import com.github.uemoo.android.domain.config.AppConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    fun provideRetrofit(appConfig: AppConfig): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        val converterFactory = json.asConverterFactory(contentType)

        val client = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(converterFactory)
            .baseUrl(appConfig.baseUrl)
            .build()
    }
}
