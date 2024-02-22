package com.github.uemoo.android.data.network.interceptor

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import okhttp3.Interceptor
import javax.inject.Qualifier

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal interface InterceptorModule {
    @OkHttpInterceptor
    @Multibinds
    fun bindOkHttpInterceptors(): MutableSet<Interceptor>

    @OkHttpNetworkInterceptor
    @Multibinds
    fun bindOkHttpNetworkInterceptors(): MutableSet<Interceptor>

    @OkHttpInterceptor
    @IntoSet
    @Binds
    fun bindAddAuthHeaderInterceptor(impl: AddAuthHeaderInterceptor): Interceptor

    @OkHttpInterceptor
    @IntoSet
    @Binds
    fun bindCheckApiResponseInterceptor(impl: CheckApiResponseInterceptor): Interceptor
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class OkHttpInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class OkHttpNetworkInterceptor
