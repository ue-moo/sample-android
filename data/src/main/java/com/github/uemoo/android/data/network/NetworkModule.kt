package com.github.uemoo.android.data.network

import android.util.Log
import com.github.uemoo.android.data.network.interceptor.OkHttpInterceptor
import com.github.uemoo.android.data.network.interceptor.OkHttpNetworkInterceptor
import com.github.uemoo.android.domain.config.AppConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(
        appConfig: AppConfig,
        @OkHttpInterceptor interceptors: MutableSet<Interceptor>,
        @OkHttpNetworkInterceptor networkInterceptors: MutableSet<Interceptor>,
    ): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        val converterFactory = json.asConverterFactory(contentType)

        val client = OkHttpClient.Builder()
            .apply {
                interceptors.forEachIndexed { index, interceptor ->
                    Log.d("XXX", "[${index + 1}/${interceptors.count()}] ${interceptor.javaClass.simpleName} is set.")
                    addInterceptor(interceptor)
                }
                networkInterceptors.forEachIndexed { index, interceptor ->
                    Log.d("XXX", "[${index + 1}/${networkInterceptors.count()}] ${interceptor.javaClass.simpleName} is set.")
                    addNetworkInterceptor(interceptor)
                }
            }
            .build()

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(converterFactory)
            .baseUrl(appConfig.baseUrl)
            .build()
    }
}
